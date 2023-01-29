package com.doskoch.template.anime.presentation.screens.top

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.doskoch.template.anime.domain.model.AnimeItem
import com.doskoch.template.anime.domain.model.AnimeType
import com.doskoch.template.anime.domain.screens.top.GetLastPagingKeyUseCase
import com.doskoch.template.anime.domain.screens.top.LoadAnimeUseCase
import com.doskoch.template.anime.domain.screens.top.StoreAnimeUseCase
import com.doskoch.template.anime.presentation.INITIAL_PAGE_KEY
import com.doskoch.template.core.android.components.error.ErrorMapper
import com.doskoch.template.core.android.components.error.GlobalErrorHandler
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import timber.log.Timber

@OptIn(ExperimentalPagingApi::class)
class TopAnimeRemoteMediator @AssistedInject constructor(
    @Assisted private val animeType: Int,
    private val getLastPagingKeyUseCase: GetLastPagingKeyUseCase,
    private val loadAnimeUseCase: LoadAnimeUseCase,
    private val storeAnimeUseCase: StoreAnimeUseCase,
    private val globalErrorHandler: GlobalErrorHandler,
    private val errorMapper: ErrorMapper
) : RemoteMediator<Int, AnimeItem>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, AnimeItem>): MediatorResult {
        val key = when (loadType) {
            LoadType.REFRESH -> INITIAL_PAGE_KEY
            LoadType.APPEND -> getLastPagingKeyUseCase.invoke() ?: return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
        }

        return try {
            val page = loadAnimeUseCase.invoke(
                type = AnimeType.values()[animeType],
                key = key,
                pageSize = if (key == INITIAL_PAGE_KEY) state.config.initialLoadSize else state.config.pageSize
            )

            storeAnimeUseCase.invoke(
                clearExistingData = loadType == LoadType.REFRESH,
                previousKey = if (key > INITIAL_PAGE_KEY) key - 1 else null,
                currentKey = key,
                nextKey = if (page.hasNextPage) key + 1 else null,
                page = page.items
            )

            MediatorResult.Success(endOfPaginationReached = !page.hasNextPage)
        } catch (t: Throwable) {
            Timber.e(t)
            globalErrorHandler.handle(errorMapper.toCoreError(t), showIfNotHandled = false)
            MediatorResult.Error(t)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(animeType: Int): TopAnimeRemoteMediator
    }
}
