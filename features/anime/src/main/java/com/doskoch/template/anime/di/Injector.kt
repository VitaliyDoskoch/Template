package com.doskoch.template.anime.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.doskoch.legacy.kotlin.DestroyableLazy
import com.doskoch.template.anime.INITIAL_LOAD_SIZE
import com.doskoch.template.anime.INITIAL_PAGE
import com.doskoch.template.anime.PAGE_SIZE
import com.doskoch.template.anime.data.AnimeItem
import com.doskoch.template.anime.data.AnimeType
import com.doskoch.template.anime.screens.top.TopAnimeViewModel
import com.doskoch.template.anime.screens.top.paging.AnimePagingSource
import com.doskoch.template.anime.screens.top.paging.AnimeRemoteMediator
import com.doskoch.template.anime.screens.top.useCase.LoadAnimeUseCase
import com.doskoch.template.anime.screens.top.useCase.LogoutUseCase

object AnimeFeatureInjector {
    var provider: DestroyableLazy<AnimeFeature>? = null
}

internal val Injector: AnimeFeature
    get() = AnimeFeatureInjector.provider!!.value

@ExperimentalPagingApi
object Module {

    val topAnimeViewModel: TopAnimeViewModel
        get() = TopAnimeViewModel(
            logoutUseCase = logoutUseCase,
            pagerFactory = { animeType -> pager(animeType = animeType) },
            storage = Injector.storage,
            globalErrorHandler = Injector.globalErrorHandler
        )

    private val logoutUseCase: LogoutUseCase
        get() = LogoutUseCase(
            repository = Injector.repository,
            globalNavigator = Injector.globalNavigator
        )

    private fun pager(animeType: AnimeType) = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE, initialLoadSize = INITIAL_LOAD_SIZE),
        remoteMediator = animeRemoteMediator(animeType = animeType),
        initialKey = INITIAL_PAGE,
        pagingSourceFactory = { AnimePagingSource(storage = Injector.storage) }
    )

    private fun animeRemoteMediator(animeType: AnimeType) = AnimeRemoteMediator(
        animeType = animeType,
        loadAnimeUseCase = LoadAnimeUseCase(repository = Injector.repository),
        storage = Injector.storage
    )
}