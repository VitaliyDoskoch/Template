package com.doskoch.template.anime.presentation.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import com.doskoch.template.anime.presentation.PAGING_CONFIG
import com.doskoch.template.anime.presentation.screens.top.TopAnimeRemoteMediator
import com.doskoch.template.anime.presentation.screens.top.TopAnimeViewModel
import com.doskoch.template.api.jikan.services.top.responses.GetTopAnimeResponse
import com.doskoch.template.core.android.components.paging.SimpleInMemoryStorage
import com.doskoch.template.core.android.ext.entryPoint
import com.doskoch.template.core.kotlin.di.ComponentAccessor
import com.doskoch.template.core.kotlin.di.FeatureScoped
import com.doskoch.template.database.schema.anime.DbAnimeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AnimeFeatureInjector {

    companion object {
        private fun entryPoint() = AnimeFeatureComponentAccessor.entryPoint<AnimeFeatureComponent.EntryPoint>()

        @Provides
        @FeatureScoped
        fun navigator() = entryPoint().animeFeatureNavigator()

        @Provides
        @FeatureScoped
        fun storage() = entryPoint().animeFeatureStorage()

        fun animeDetailsViewModelFactory() = entryPoint().animeDetailsViewModelFactory()

        @Provides
        fun topPagerFactory(
            remoteMediatorFactory: TopAnimeRemoteMediator.Factory,
            @FeatureScoped
            storage: SimpleInMemoryStorage<Int, GetTopAnimeResponse.Data>
        ) = TopAnimeViewModel.PagerFactory { remoteAnimeType ->
            @OptIn(ExperimentalPagingApi::class)
            Pager(
                config = PAGING_CONFIG,
                remoteMediator = remoteMediatorFactory.create(remoteAnimeType.ordinal),
                pagingSourceFactory = storage::SimplePagingSource
            )
        }

        @Provides
        fun favoritePager(dbAnimeDao: DbAnimeDao) = Pager(
            config = PAGING_CONFIG,
            pagingSourceFactory = dbAnimeDao::pagingSource
        )
    }
}

object AnimeFeatureComponentAccessor : ComponentAccessor<AnimeFeatureComponent>()
