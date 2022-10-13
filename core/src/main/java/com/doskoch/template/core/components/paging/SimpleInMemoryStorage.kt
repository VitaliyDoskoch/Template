package com.doskoch.template.core.components.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import timber.log.Timber
import kotlin.math.min

//TODO: remove logging
class SimpleInMemoryStorage<K : Any, V : Any> {

    private val invalidationCallbacks = mutableSetOf<() -> Unit>()

    private val storage = mutableMapOf<K, List<V>?>()

    val pages: Map<K, List<V>?>
        get() = storage.toMap()

    fun inTransaction(action: SimpleInMemoryStorage<K, V>.Modifier.() -> Unit) = synchronized(storage) {
        action(Modifier())
        invalidationCallbacks.toMutableList().forEach { it.invoke() }
    }

    inner class Modifier {

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

        fun clear() {
            storage.clear()
        }
    }

    inner class SimplePagingSource : PagingSource<Int, V>() {

        override val jumpingSupported = true

        private val items: List<V> by lazy { pages.values.filterNotNull().flatten() }

        init {
            val invalidationCallback = { Timber.e("invalidation"); invalidate() }

            registerInvalidatedCallback { invalidationCallbacks.remove(invalidationCallback) }
            invalidationCallbacks.add(invalidationCallback)
        }

        override fun getRefreshKey(state: PagingState<Int, V>): Int? = state.anchorPosition
            ?.also { Timber.e("anchorPosition: $it") }
            ?.coerceAtMost(items.lastIndex)
            ?.let { it - state.config.initialLoadSize / 2 }
            ?.coerceAtLeast(0)

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, V> = try {
            val key = params.key ?: 0
            val limit = if (params is LoadParams.Prepend) min(key, params.loadSize) else params.loadSize
            val offset = if (params is LoadParams.Prepend) (key - params.loadSize).coerceAtLeast(0) else key

            LoadResult.Page(
                data = items.drop(offset).take(limit),
                prevKey = offset.takeIf { it > 0 },
                nextKey = (offset + limit).takeIf { it in items.indices },
                itemsBefore = offset,
                itemsAfter = (items.size - (offset + limit)).coerceAtLeast(0)
            ).also {
                Timber.e(
                    "key = $key, limit = $limit, offset = $offset, " +
                        "items = ${it.data.size}, storageItems = ${items.size}, " +
                        "prevKey = ${it.prevKey}, nextKey = ${it.nextKey}, " +
                        "itemsBefore = ${it.itemsBefore}, itemsAfter = ${it.itemsAfter}"
                )
            }
                .let {
                    if (invalid) {
                        Timber.e("invalid")
                        LoadResult.Invalid()
                    } else it
                }
        } catch (t: Throwable) {
            Timber.e(t)
            LoadResult.Error(t)
        }
    }
}