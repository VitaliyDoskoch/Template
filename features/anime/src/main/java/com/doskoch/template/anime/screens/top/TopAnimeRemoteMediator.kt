package com.doskoch.template.anime.screens.top

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.doskoch.template.anime.INITIAL_PAGE
import com.doskoch.template.anime.screens.top.useCase.LoadAnimeUseCase
import com.doskoch.template.api.jikan.common.enum.AnimeType
import com.doskoch.template.api.jikan.services.responses.GetTopAnimeResponse
import com.doskoch.template.core.components.paging.SimpleInMemoryStorage
import timber.log.Timber

@OptIn(ExperimentalPagingApi::class)
class TopAnimeRemoteMediator(
    private val animeType: AnimeType,
    private val loadAnimeUseCase: LoadAnimeUseCase,
    private val storage: SimpleInMemoryStorage<Int, GetTopAnimeResponse.Data>
) : RemoteMediator<Int, GetTopAnimeResponse.Data>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, GetTopAnimeResponse.Data>): MediatorResult {
        val key = when(loadType) {
            LoadType.REFRESH -> INITIAL_PAGE
            LoadType.APPEND -> storage.pages.keys.lastOrNull() ?: return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
        }

        return try {
            val response = loadAnimeUseCase.invoke(
                type = animeType,
                key = key,
                pageSize = if(key == INITIAL_PAGE) state.config.initialLoadSize else state.config.pageSize
            )

            storage.inTransaction {
                if(loadType == LoadType.REFRESH) {
                    update(emptyMap())
                }

                store(
                    previousKey = if(key > INITIAL_PAGE) key - 1 else null,
                    currentKey = key,
                    nextKey = if(response.pagination.hasNextPage) key + 1 else null,
                    page = response.data
                )
            }

            MediatorResult.Success(endOfPaginationReached = !response.pagination.hasNextPage)
        } catch (t: Throwable) {
            Timber.e(t)
            MediatorResult.Error(t)
        }
    }
}