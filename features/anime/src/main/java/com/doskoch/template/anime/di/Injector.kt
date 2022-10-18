package com.doskoch.template.anime.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import com.doskoch.legacy.kotlin.DestroyableLazy
import com.doskoch.template.anime.PAGING_CONFIG
import com.doskoch.template.anime.screens.details.AnimeDetailsViewModel
import com.doskoch.template.anime.screens.favorite.FavoriteAnimeViewModel
import com.doskoch.template.anime.screens.top.TopAnimeRemoteMediator
import com.doskoch.template.anime.screens.top.TopAnimeViewModel
import com.doskoch.template.anime.screens.top.useCase.ClearAnimeUseCase
import com.doskoch.template.anime.screens.top.useCase.GetFavoriteAnimeIdsUseCase
import com.doskoch.template.anime.screens.top.useCase.LoadAnimeUseCase
import com.doskoch.template.anime.useCase.DeleteAnimeFromFavoriteUseCase
import com.doskoch.template.anime.useCase.SaveAnimeToFavoriteUseCase
import com.doskoch.template.api.jikan.common.enum.RemoteAnimeType
import com.doskoch.template.api.jikan.services.responses.GetTopAnimeResponse
import com.doskoch.template.core.components.paging.SimpleInMemoryStorage
import com.doskoch.template.core.useCase.authorization.LogoutUseCase
import timber.log.Timber

object AnimeFeatureInjector {
    var provider: DestroyableLazy<AnimeFeature>? = null
}

internal val Injector: AnimeFeature
    get() = AnimeFeatureInjector.provider!!.value

@ExperimentalPagingApi
object Module {

    private val topAnimeStorage = DestroyableLazy(initialize = { SimpleInMemoryStorage<Int, GetTopAnimeResponse.Data>() })

    fun topAnimeViewModel() = TopAnimeViewModel(
        navigator = Injector.navigator,
        pagerFactory = { remoteAnimeType ->
            Pager(
                config = PAGING_CONFIG,
                remoteMediator = TopAnimeRemoteMediator(
                    remoteAnimeType = remoteAnimeType,
                    loadAnimeUseCase = LoadAnimeUseCase(service = Injector.topService),
                    storage = topAnimeStorage.value
                ),
                pagingSourceFactory = topAnimeStorage.value::SimplePagingSource
            )
        },
        clearAnimeUseCase = ClearAnimeUseCase(storage = topAnimeStorage.value),
        getFavoriteAnimeIdsUseCase = GetFavoriteAnimeIdsUseCase(dbAnimeDao = Injector.dbAnimeDao),
        saveAnimeToFavoriteUseCase = SaveAnimeToFavoriteUseCase(storage = topAnimeStorage.value, dbAnimeDao = Injector.dbAnimeDao),
        deleteAnimeFromFavoriteUseCase = DeleteAnimeFromFavoriteUseCase(dbAnimeDao = Injector.dbAnimeDao),
        logoutUseCase = LogoutUseCase(store = Injector.authorizationDataStore),
        globalErrorHandler = Injector.globalErrorHandler
    ).also {
        it.addCloseable { topAnimeStorage.destroyInstance() }
    }

    fun favoriteAnimeViewModel() = FavoriteAnimeViewModel(
        navigator = Injector.navigator,
        pager = Pager(
            config = PAGING_CONFIG,
            pagingSourceFactory = Injector.dbAnimeDao::pagingSource
        )
    )

    fun animeDetailsViewModel() = AnimeDetailsViewModel()
}