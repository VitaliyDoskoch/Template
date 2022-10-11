package com.doskoch.template.anime.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.doskoch.legacy.kotlin.DestroyableLazy
import com.doskoch.template.anime.INITIAL_LOAD_SIZE
import com.doskoch.template.anime.PAGE_SIZE
import com.doskoch.template.anime.data.AnimeType
import com.doskoch.template.anime.screens.details.AnimeDetailsViewModel
import com.doskoch.template.anime.screens.favorite.FavoriteAnimeViewModel
import com.doskoch.template.anime.screens.top.TopAnimeViewModel
import com.doskoch.template.anime.screens.top.paging.AnimeRemoteMediator
import com.doskoch.template.anime.screens.top.useCase.LoadAnimeUseCase

object AnimeFeatureInjector {
    var provider: DestroyableLazy<AnimeFeature>? = null
}

internal val Injector: AnimeFeature
    get() = AnimeFeatureInjector.provider!!.value

@ExperimentalPagingApi
object Module {

    fun topAnimeViewModel() = TopAnimeViewModel(
        logoutUseCase = Injector.logoutUseCase,
        pagerFactory = { animeType -> pager(animeType = animeType) },
        globalErrorHandler = Injector.globalErrorHandler,
        navigator = Injector.navigator
    )

    private fun pager(animeType: AnimeType) = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE, initialLoadSize = INITIAL_LOAD_SIZE, enablePlaceholders = true),
        remoteMediator = animeRemoteMediator(animeType = animeType),
        pagingSourceFactory = { Injector.storage.SimplePagingSource() }
    )

    private fun animeRemoteMediator(animeType: AnimeType) = AnimeRemoteMediator(
        animeType = animeType,
        loadAnimeUseCase = LoadAnimeUseCase(repository = Injector.repository),
        storage = Injector.storage
    )

    fun favoriteAnimeViewModel() = FavoriteAnimeViewModel()

    fun animeDetailsViewModel() = AnimeDetailsViewModel()
}