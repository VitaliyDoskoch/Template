package com.doskoch.template.anime.top.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.doskoch.template.anime.INITIAL_PAGE
import com.doskoch.template.anime.data.AnimeItem
import com.doskoch.template.core.components.paging.SimpleInMemoryStorage

class AnimeInMemoryPagingSource(
    private val storage: SimpleInMemoryStorage<Int, AnimeItem>
) : PagingSource<Int, AnimeItem>(){

    private val invalidationCallback = { invalidate() }

    init {
        storage.addInvalidationCallback(invalidationCallback)
        registerInvalidatedCallback { storage.removeInvalidationCallback(invalidationCallback) }
    }

    override fun getRefreshKey(state: PagingState<Int, AnimeItem>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeItem> {
        return try {
            val key = params.key ?: INITIAL_PAGE

            val keys = storage.keysOf(key)
            val items = storage.pageOf(key).orEmpty()

            LoadResult.Page(
                data = items,
                prevKey = keys?.previous,
                nextKey = keys?.next
            )
        } catch (t: Throwable) {
            LoadResult.Error(t)
        }
    }
}