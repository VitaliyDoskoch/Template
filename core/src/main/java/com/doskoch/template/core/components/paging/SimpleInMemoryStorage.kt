package com.doskoch.template.core.components.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import timber.log.Timber
import kotlin.math.min

//TODO: remove logging
class SimpleInMemoryStorage<K : Any, V : Any> {

    private val invalidationCallbacks = mutableSetOf<() -> Unit>()

    private val storage = mutableMapOf<PageKeys<K>, List<V>>()

    val items: List<V>
        get() = storage.values.flatten()

    val keys: Set<PageKeys<K>>
        get() = storage.keys

    fun inTransaction(action: SimpleInMemoryStorage<K, V>.Modifier.() -> Unit) = synchronized(storage) {
        action(Modifier())
        invalidationCallbacks.toMutableList().forEach { it.invoke() }
    }

    data class PageKeys<K>(val previous: K?, val current: K, val next: K?)

    inner class Modifier {

        fun store(previousKey: K?, currentKey: K, nextKey: K?, page: List<V>) {
            storage[PageKeys(previousKey, currentKey, nextKey)] = page
        }

        fun clear() {
            storage.clear()
        }
    }

    inner class SimplePagingSource : PagingSource<Int, V>() {

        private val items = this@SimpleInMemoryStorage.items.toMutableList()

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
            val limit = if(params is LoadParams.Prepend) min(key, params.loadSize) else params.loadSize
            val offset = if(params is LoadParams.Prepend) (key - params.loadSize).coerceAtLeast(0) else key

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
                    if(invalid) {
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