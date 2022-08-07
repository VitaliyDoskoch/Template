package com.doskoch.template.anime.top.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.doskoch.template.anime.INITIAL_PAGE
import com.doskoch.template.anime.data.AnimeFilter
import com.doskoch.template.anime.data.AnimeItem
import com.doskoch.template.anime.data.AnimeType
import com.doskoch.template.anime.di.AnimeFeatureRepository
import com.doskoch.template.anime.top.useCase.LoadAnimeUseCase
import com.doskoch.template.core.paging.SimpleInMemoryStorage
import timber.log.Timber

@OptIn(ExperimentalPagingApi::class)
class AnimeRemoteMediator(
    private val loadAnimeUseCase: LoadAnimeUseCase,
    private val storage: SimpleInMemoryStorage<Int, AnimeItem>
) : RemoteMediator<Int, AnimeItem>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, AnimeItem>): MediatorResult {
        val key = when(loadType) {
            LoadType.REFRESH -> INITIAL_PAGE
            LoadType.APPEND -> storage.lastKeys?.next ?: return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
        }

        return try {
            val data = loadAnimeUseCase.invoke(
                AnimeType.Tv,
                AnimeFilter.Popularity,
                key = key,
                pageSize = if(key == INITIAL_PAGE) state.config.initialLoadSize else state.config.pageSize
            )

            if(loadType == LoadType.REFRESH) {
                storage.clear()
            }

            storage.store(
                prevKey = key.takeIf { it > INITIAL_PAGE }?.let { it - 1 },
                nextKey = data.lastPage.takeIf { data.hasNext },
                page = data.items
            )

            MediatorResult.Success(endOfPaginationReached = data.hasNext)
        } catch (t: Throwable) {
            Timber.e(t)
            MediatorResult.Error(t)
        }
    }

}