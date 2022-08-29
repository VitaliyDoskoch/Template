package com.doskoch.template.anime.screens.top.paging

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult.Page.Companion.COUNT_UNDEFINED
import androidx.paging.PagingState
import com.doskoch.template.anime.INITIAL_PAGE
import com.doskoch.template.anime.data.AnimeItem
import com.doskoch.template.core.components.paging.SimpleInMemoryStorage
import timber.log.Timber

class AnimePagingSource(
    private val storage: SimpleInMemoryStorage<Int, AnimeItem>
) : PagingSource<Int, AnimeItem>() {

    private val invalidationCallback = { invalidate() }

    init {
        storage.addInvalidationCallback(invalidationCallback)
        registerInvalidatedCallback { storage.removeInvalidationCallback(invalidationCallback) }
    }

    override fun getRefreshKey(state: PagingState<Int, AnimeItem>): Int? = state.anchorPosition?.let { anchorPosition ->
        val anchorPage = state.closestPageToPosition(anchorPosition)
        storage.keys.find { storage.pageOf(it.current) === anchorPage?.data }?.current
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeItem> {
        return try {
            val key = params.key ?: INITIAL_PAGE

            val keys = storage.keysOf(key)
            val items = storage.pageOf(key).orEmpty()

            val itemsBefore = items.firstOrNull()?.let { storage.items.indexOf(it) } ?: 0
            val itemsAfter = items.lastOrNull()?.let { storage.items.indices.last - storage.items.indexOf(it) } ?: 0

            LoadResult.Page(
                data = items,
                prevKey = keys?.previous,
                nextKey = keys?.next,
                itemsBefore = itemsBefore,
                itemsAfter = itemsAfter
            )
        } catch (t: Throwable) {
            Timber.e(t)
            LoadResult.Error(t)
        }
    }
}