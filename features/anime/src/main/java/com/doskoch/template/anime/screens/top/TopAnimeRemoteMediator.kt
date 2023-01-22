package com.doskoch.template.anime.screens.top

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.doskoch.template.anime.INITIAL_PAGE_KEY
import com.doskoch.template.anime.screens.top.useCase.GetLastPagingKeyUseCase
import com.doskoch.template.anime.screens.top.useCase.LoadAnimeUseCase
import com.doskoch.template.anime.screens.top.useCase.StoreAnimeUseCase
import com.doskoch.template.api.jikan.common.enum.RemoteAnimeType
import com.doskoch.template.api.jikan.services.top.responses.GetTopAnimeResponse
import com.doskoch.template.core.android.components.error.GlobalErrorHandler
import com.doskoch.template.core.android.components.error.toCoreError
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import timber.log.Timber

@OptIn(ExperimentalPagingApi::class)
class TopAnimeRemoteMediator @AssistedInject constructor(
    @Assisted private val remoteAnimeType: Int,
    private val getLastPagingKeyUseCase: GetLastPagingKeyUseCase,
    private val loadAnimeUseCase: LoadAnimeUseCase,
    private val storeAnimeUseCase: StoreAnimeUseCase,
    private val globalErrorHandler: GlobalErrorHandler
) : RemoteMediator<Int, GetTopAnimeResponse.Data>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, GetTopAnimeResponse.Data>): MediatorResult {
        val key = when (loadType) {
            LoadType.REFRESH -> INITIAL_PAGE_KEY
            LoadType.APPEND -> getLastPagingKeyUseCase.invoke() ?: return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
        }

        return try {
            val response = loadAnimeUseCase.invoke(
                type = RemoteAnimeType.values()[remoteAnimeType],
                key = key,
                pageSize = if (key == INITIAL_PAGE_KEY) state.config.initialLoadSize else state.config.pageSize
            )

            storeAnimeUseCase.invoke(
                clearExistingData = loadType == LoadType.REFRESH,
                previousKey = if (key > INITIAL_PAGE_KEY) key - 1 else null,
                currentKey = key,
                nextKey = if (response.pagination.hasNextPage) key + 1 else null,
                page = response.data
            )

            MediatorResult.Success(endOfPaginationReached = !response.pagination.hasNextPage)
        } catch (t: Throwable) {
            Timber.e(t)
            globalErrorHandler.handle(t.toCoreError(), showIfNotHandled = false)
            MediatorResult.Error(t)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(remoteAnimeType: Int): TopAnimeRemoteMediator
    }
}
