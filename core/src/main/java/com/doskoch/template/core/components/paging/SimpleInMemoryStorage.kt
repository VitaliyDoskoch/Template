package com.doskoch.template.core.components.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import timber.log.Timber

@Suppress("MemberVisibilityCanBePrivate")
class SimpleInMemoryStorage<K : Any, V : Any> {

    private val invalidationCallbacks = mutableListOf<() -> Unit>()

    private val storage = mutableMapOf<PageKeys<K>, List<V>>()

    val items: List<V>
        get() = storage.values.flatten()

    val keys: Set<PageKeys<K>>
        get() = storage.keys

    fun keysOf(currentKey: K) = keys.find { it.current == currentKey }

    fun pageOf(currentKey: K) = keys
        .find { it.current == currentKey }
        ?.let { storage[it] }

    fun inTransaction(action: SimpleInMemoryStorage<K, V>.Modifier.() -> Unit) = synchronized(storage) {
        action(Modifier())
        triggerInvalidation()
    }

    private fun triggerInvalidation() {
        invalidationCallbacks.toMutableList().forEach { it.invoke() }
    }

    inner class Modifier {

        fun store(previousKey: K?, currentKey: K, nextKey: K?, page: List<V>) {
            storage[PageKeys(previousKey, currentKey, nextKey)] = page
        }

        fun clear() {
            storage.clear()
        }
    }

    data class PageKeys<K>(val previous: K?, val current: K, val next: K?)

    inner class SimplePagingSource : PagingSource<Int, V>() {

        private val pageSize = 10

        init {
            val invalidationCallback = { Timber.e("invalidation"); invalidate() }

            invalidationCallbacks.add(invalidationCallback)
            registerInvalidatedCallback { invalidationCallbacks.remove(invalidationCallback) }
        }

        override fun getRefreshKey(state: PagingState<Int, V>): Int? = state.anchorPosition
            ?.also { Timber.e("anchorPosition: $it") }
            ?.let { state.closestPageToPosition(it)?.data?.firstOrNull() }
            ?.let { items.indexOf(it) }
            ?.takeIf { it != -1 }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, V> = try {
            val position = params.key ?: 0

            LoadResult.Page(
                data = items.drop(position).take(pageSize),
                prevKey = (position - pageSize).takeIf { it in items.indices },
                nextKey = (position + pageSize).takeIf { it in items.indices },
                itemsBefore = position,
                itemsAfter = (items.size - (position + pageSize)).coerceAtLeast(0)
            ).also {
                Timber.e("position = $position, prevKey = ${it.prevKey}, nextKey = ${it.nextKey}, itemsBefore = ${it.itemsBefore}, itemsAfter = ${it.itemsAfter}, items = ${it.data.size}, storageItems = ${items.size}")
            }
        } catch (t: Throwable) {
            Timber.e(t)
            LoadResult.Error(t)
        }
    }
}