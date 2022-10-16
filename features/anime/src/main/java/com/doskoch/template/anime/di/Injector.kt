package com.doskoch.template.anime.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.doskoch.legacy.kotlin.DestroyableLazy
import com.doskoch.template.anime.INITIAL_LOAD_SIZE
import com.doskoch.template.anime.PAGE_SIZE
import com.doskoch.template.anime.screens.details.AnimeDetailsViewModel
import com.doskoch.template.anime.screens.favorite.FavoriteAnimeViewModel
import com.doskoch.template.anime.screens.top.TopAnimeRemoteMediator
import com.doskoch.template.anime.screens.top.TopAnimeViewModel
import com.doskoch.template.anime.useCase.LoadAnimeUseCase
import com.doskoch.template.api.jikan.common.enum.RemoteAnimeType
import com.doskoch.template.core.useCase.authorization.LogoutUseCase

object AnimeFeatureInjector {
    var provider: DestroyableLazy<AnimeFeature>? = null
}

internal val Injector: AnimeFeature
    get() = AnimeFeatureInjector.provider!!.value

@ExperimentalPagingApi
object Module {

    fun topAnimeViewModel() = TopAnimeViewModel(
        logoutUseCase = LogoutUseCase(store = Injector.authorizationDataStore),
        pagerFactory = { animeType -> pager(remoteAnimeType = animeType) },
        globalErrorHandler = Injector.globalErrorHandler,
        navigator = Injector.navigator
    )

    private fun pager(remoteAnimeType: RemoteAnimeType) = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE, initialLoadSize = INITIAL_LOAD_SIZE, enablePlaceholders = true),
        remoteMediator = animeRemoteMediator(remoteAnimeType = remoteAnimeType),
        pagingSourceFactory = { Injector.storage.SimplePagingSource() }
    )

    private fun animeRemoteMediator(remoteAnimeType: RemoteAnimeType) = TopAnimeRemoteMediator(
        remoteAnimeType = remoteAnimeType,
        loadAnimeUseCase = LoadAnimeUseCase(service = Injector.topService),
        storage = Injector.storage
    )

    fun favoriteAnimeViewModel() = FavoriteAnimeViewModel(
        navigator = Injector.navigator
    )

    fun animeDetailsViewModel() = AnimeDetailsViewModel()
}