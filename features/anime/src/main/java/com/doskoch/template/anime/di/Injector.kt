package com.doskoch.template.anime.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.doskoch.legacy.kotlin.DestroyableLazy
import com.doskoch.template.anime.INITIAL_LOAD_SIZE
import com.doskoch.template.anime.INITIAL_PAGE
import com.doskoch.template.anime.PAGE_SIZE
import com.doskoch.template.anime.data.AnimeItem
import com.doskoch.template.anime.screens.top.TopAnimeViewModel
import com.doskoch.template.anime.screens.top.paging.AnimePagingSource
import com.doskoch.template.anime.screens.top.paging.AnimeRemoteMediator
import com.doskoch.template.anime.screens.top.useCase.LoadAnimeUseCase

object AnimeFeatureInjector {
    var provider: DestroyableLazy<AnimeFeature>? = null
}

internal val Injector: AnimeFeature
    get() = AnimeFeatureInjector.provider!!.value

@ExperimentalPagingApi
object Module {

    val topAnimeViewModel: TopAnimeViewModel
        get() = TopAnimeViewModel(pager = pager)

    private val animeRemoteMediator: AnimeRemoteMediator
        get() = AnimeRemoteMediator(
            loadAnimeUseCase = LoadAnimeUseCase(repository = Injector.repository),
            storage = Injector.storage
        )

    private val pager: Pager<Int, AnimeItem>
        get() = Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, initialLoadSize = INITIAL_LOAD_SIZE),
            remoteMediator = animeRemoteMediator,
            initialKey = INITIAL_PAGE,
            pagingSourceFactory = { AnimePagingSource(storage = Injector.storage) }
        )
}