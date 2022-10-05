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

    fun addInvalidationCallback(callback: () -> Unit) {
        invalidationCallbacks.add(callback)
    }

    fun removeInvalidationCallback(callback: () -> Unit) {
        invalidationCallbacks.remove(callback)
    }

    fun keysOf(key: K) = keys.find { it.current == key }

    fun pageOf(key: K) = keys
        .find { it.current == key }
        ?.let { storage[it] }

    fun store(previousKey: K?, currentKey: K, nextKey: K?, page: List<V>) = synchronized(storage){
        storage[PageKeys(previousKey, currentKey, nextKey)] = page
        triggerInvalidation()
    }

    fun clear() = synchronized(storage){
        storage.clear()
        triggerInvalidation()
    }

    private fun triggerInvalidation() {
        invalidationCallbacks.toMutableList().forEach { it.invoke() }
    }

    data class PageKeys<K>(val previous: K?, val current: K, val next: K?)

    inner class SimplePagingSource(val initialKey: K) : PagingSource<K, V>() {

        init {
            val invalidationCallback = { invalidate() }

            addInvalidationCallback(invalidationCallback)
            registerInvalidatedCallback { removeInvalidationCallback(invalidationCallback) }
        }

        override fun getRefreshKey(state: PagingState<K, V>): K? = state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            keys.find { pageOf(it.current) === anchorPage?.data }?.current
        }

        override suspend fun load(params: LoadParams<K>): LoadResult<K, V> = try {
            val key = params.key ?: initialKey

            val keys = keysOf(key)
            val items = pageOf(key).orEmpty()

            LoadResult.Page(
                data = items,
                prevKey = keys?.previous,
                nextKey = keys?.next,
//                itemsBefore = itemsBefore,
//                itemsAfter = itemsAfter
            )
        } catch (t: Throwable) {
            Timber.e(t)
            LoadResult.Error(t)
        }
    }
}