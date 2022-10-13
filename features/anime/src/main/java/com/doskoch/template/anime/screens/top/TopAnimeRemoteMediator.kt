package com.doskoch.template.anime.screens.top

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.doskoch.template.anime.INITIAL_PAGE
import com.doskoch.template.anime.data.AnimeItem
import com.doskoch.template.anime.data.AnimeType
import com.doskoch.template.anime.screens.top.useCase.LoadAnimeUseCase
import com.doskoch.template.core.components.paging.SimpleInMemoryStorage
import timber.log.Timber

@OptIn(ExperimentalPagingApi::class)
class TopAnimeRemoteMediator(
    private val animeType: AnimeType,
    private val loadAnimeUseCase: LoadAnimeUseCase,
    private val storage: SimpleInMemoryStorage<Int, AnimeItem>
) : RemoteMediator<Int, AnimeItem>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, AnimeItem>): MediatorResult {
        val key = when(loadType) {
            LoadType.REFRESH -> INITIAL_PAGE
            LoadType.APPEND -> storage.pages.keys.lastOrNull() ?: return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
        }

        return try {
            val data = loadAnimeUseCase.invoke(
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
                    nextKey = if(data.hasNext) key + 1 else null,
                    page = data.items
                )
            }

            MediatorResult.Success(endOfPaginationReached = !data.hasNext)
        } catch (t: Throwable) {
            Timber.e(t)
            MediatorResult.Error(t)
        }
    }
}