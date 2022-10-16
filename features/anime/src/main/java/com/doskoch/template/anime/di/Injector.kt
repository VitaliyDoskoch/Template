package com.doskoch.template.anime.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import com.doskoch.legacy.kotlin.DestroyableLazy
import com.doskoch.template.anime.PAGING_CONFIG
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
        navigator = Injector.navigator,
        pagerFactory = { remoteAnimeType ->
            Pager(
                config = PAGING_CONFIG,
                remoteMediator = animeRemoteMediator(remoteAnimeType = remoteAnimeType),
                pagingSourceFactory = { Injector.storage.SimplePagingSource() }
            )
        },
        logoutUseCase = LogoutUseCase(store = Injector.authorizationDataStore),
        globalErrorHandler = Injector.globalErrorHandler
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