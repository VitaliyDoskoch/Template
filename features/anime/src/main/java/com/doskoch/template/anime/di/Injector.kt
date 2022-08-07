package com.doskoch.template.anime.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.doskoch.legacy.kotlin.DestroyableLazy
import com.doskoch.template.anime.INITIAL_PAGE
import com.doskoch.template.anime.PAGE_SIZE
import com.doskoch.template.anime.top.TopAnimeViewModel
import com.doskoch.template.anime.top.paging.AnimeInMemoryPagingSource
import com.doskoch.template.anime.top.paging.AnimeRemoteMediator
import com.doskoch.template.anime.top.useCase.LoadAnimeUseCase

object AnimeFeatureInjector {
    var provider: DestroyableLazy<AnimeFeature>? = null
}

internal val Injector: AnimeFeature
    get() = AnimeFeatureInjector.provider!!.value

@ExperimentalPagingApi
object Module {

    val topAnimeViewModel: TopAnimeViewModel
        get() = TopAnimeViewModel(
            pager = Pager(
                config = PagingConfig(pageSize = PAGE_SIZE),
                remoteMediator = animeRemoteMediator,
                initialKey = INITIAL_PAGE,
                pagingSourceFactory = { AnimeInMemoryPagingSource(storage = Injector.storage) }
            )
        )

    private val animeRemoteMediator: AnimeRemoteMediator
        get() = AnimeRemoteMediator(
            loadAnimeUseCase = LoadAnimeUseCase(repository = Injector.repository),
            storage = Injector.storage
        )
}