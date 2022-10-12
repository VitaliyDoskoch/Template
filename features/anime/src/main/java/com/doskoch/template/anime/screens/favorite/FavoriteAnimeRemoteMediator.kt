package com.doskoch.template.anime.screens.favorite

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.doskoch.template.anime.INITIAL_PAGE
import com.doskoch.template.anime.data.AnimeItem
import com.doskoch.template.anime.data.AnimeType
import com.doskoch.template.anime.screens.top.useCase.LoadAnimeUseCase
import com.doskoch.template.core.components.paging.SimpleInMemoryStorage
import com.doskoch.template.database.schema.anime.DbAnime
import com.doskoch.template.database.schema.anime.DbAnimeDao
import timber.log.Timber

@OptIn(ExperimentalPagingApi::class)
class FavoriteAnimeRemoteMediator(
    private val loadAnimeUseCase: LoadAnimeUseCase,
    private val dao: DbAnimeDao,
    private val converter: Converter
) : RemoteMediator<Int, DbAnime>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, DbAnime>): MediatorResult {
        val key = when(loadType) {
            LoadType.REFRESH -> INITIAL_PAGE
            LoadType.APPEND -> state.pages.size + 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
        }

        return try {
            val data = loadAnimeUseCase.invoke(
                type = AnimeType.Tv,
                key = key,
                pageSize = if(key == INITIAL_PAGE) state.config.initialLoadSize else state.config.pageSize
            )

            dao.database.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    dao.clear()
                }

                dao.insert(data.items.map(converter::toDbAnime))
            }

            MediatorResult.Success(endOfPaginationReached = !data.hasNext)
        } catch (t: Throwable) {
            Timber.e(t)
            MediatorResult.Error(t)
        }
    }
}