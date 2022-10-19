package com.doskoch.template.core.components.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import kotlin.math.min

class SimpleInMemoryStorage<K : Any, V : Any> {

    private val invalidationCallbacks = mutableSetOf<() -> Unit>()

    private val storage = mutableMapOf<K, List<V>?>()

    private val _pages = MutableStateFlow(storage.toMap())
    val pages = _pages.asStateFlow()

    fun inTransaction(action: SimpleInMemoryStorage<K, V>.Modifier.() -> Unit) = synchronized(storage) {
        action(Modifier())
        _pages.value = storage.toMap()
        invalidationCallbacks.toMutableList().forEach { it.invoke() }
    }

    inner class Modifier internal constructor() {

        fun store(previousKey: K?, currentKey: K, nextKey: K?, page: List<V>) {
            if (storage.isEmpty()) {
                previousKey?.let { storage[it] = null }
                storage[currentKey] = page
                nextKey?.let { storage[it] = null }
            } else {
                val keys = storage.keys.toList()
                val index = keys.indexOf(currentKey)

                when {
                    index == -1 -> throw IllegalArgumentException(
                        "currentKey ($currentKey) is not attached to any existing page via previousKey or nextKey parameters"
                    )
                    nextKey != null && nextKey == keys.getOrNull(index + 1) -> {
                        val newMap = mutableMapOf<K, List<V>?>()

                        previousKey?.let { newMap.putIfAbsent(it, null) }
                        newMap[currentKey] = page
                        newMap.putAll(storage)

                        storage.clear()
                        storage.putAll(newMap)
                    }
                    previousKey != null && previousKey == keys.getOrNull(index - 1) -> {
                        storage[currentKey] = page
                        nextKey?.let { storage.putIfAbsent(it, null) }
                    }
                    else -> throw IllegalArgumentException(
                        "previousKey ($previousKey) or nextKey ($nextKey) parameters must point to an existing page"
                    )
                }
            }
        }

        fun updatePage(key: K, value: List<V>?) = storage.replace(key, value)

        fun removePage(key: K) = storage.remove(key)

        fun replaceItem(newValue: V, predicate: (V) -> Boolean) = storage.locateItem(predicate)?.let { (key, page) ->
            val newPage = page.toMutableList().apply { set(page.indexOfFirst(predicate), newValue) }
            storage.replace(key, page, newPage)
        } ?: false

        fun clear() = storage.clear()
    }

    inner class SimplePagingSource : PagingSource<Int, V>() {

        override val jumpingSupported = true

        private val items: List<V> by lazy { pages.value.values.filterNotNull().flatten() }

        init {
            val invalidationCallback = { invalidate() }

            registerInvalidatedCallback { invalidationCallbacks.remove(invalidationCallback) }
            invalidationCallbacks.add(invalidationCallback)
        }

        override fun getRefreshKey(state: PagingState<Int, V>): Int? = state.anchorPosition
            ?.coerceAtMost(items.lastIndex)
            ?.let { it - state.config.initialLoadSize / 2 }
            ?.coerceAtLeast(0)

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, V> = try {
            val key = params.key ?: 0
            val limit = if (params is LoadParams.Prepend) min(key, params.loadSize) else params.loadSize
            val offset = if (params is LoadParams.Prepend) (key - params.loadSize).coerceAtLeast(0) else key

            val result = LoadResult.Page(
                data = items.drop(offset).take(limit),
                prevKey = offset.takeIf { it > 0 },
                nextKey = (offset + limit).takeIf { it in items.indices },
                itemsBefore = offset,
                itemsAfter = (items.size - (offset + limit)).coerceAtLeast(0)
            )

            if (invalid) LoadResult.Invalid() else result
        } catch (t: Throwable) {
            Timber.e(t)
            LoadResult.Error(t)
        }
    }
}

fun <K : Any, V : Any> Map<K, List<V>?>.locateItem(predicate: (V) -> Boolean): Map.Entry<K, List<V>>? {
    this.forEach { entry ->
        @Suppress("UNCHECKED_CAST")
        entry.value?.find(predicate)?.let { return entry as Map.Entry<K, List<V>> }
    }
    return null
}