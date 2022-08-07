package com.doskoch.template.anime.top.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.doskoch.template.anime.data.AnimeItem
import com.doskoch.template.core.paging.SimpleInMemoryStorage

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
            val startPosition = params.key ?: 0

            val items = storage.items
                .drop(startPosition)
                .take(params.loadSize)

            LoadResult.Page(
                data = items,
                prevKey = null,
                nextKey = (startPosition + params.loadSize).takeIf { items.size == params.loadSize }
            )
        } catch (t: Throwable) {
            LoadResult.Error(t)
        }
    }
}