package com.doskoch.template.anime.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import com.doskoch.template.anime.PAGING_CONFIG
import com.doskoch.template.anime.screens.top.TopAnimeRemoteMediator
import com.doskoch.template.anime.screens.top.TopAnimeViewModel
import com.doskoch.template.api.jikan.services.top.responses.GetTopAnimeResponse
import com.doskoch.template.core.android.components.paging.SimpleInMemoryStorage
import com.doskoch.template.core.kotlin.lazy.DestroyableLazy
import com.doskoch.template.database.schema.anime.DbAnimeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface AnimeFeatureInjector {

    companion object {
        var component: DestroyableLazy<AnimeFeatureComponent>? = null

        private val entryPoint: AnimeFeatureComponent.EntryPoint
            get() = EntryPoints.get(component!!.value, AnimeFeatureComponent.EntryPoint::class.java)

        @Provides
        fun navigator() = entryPoint.navigator()

        @Provides
        fun storage() = entryPoint.storage()

        fun animeDetailsViewModelFactory() = entryPoint.animeDetailsViewModelFactory()

        @Provides
        fun topPagerFactory(
            remoteMediatorFactory: TopAnimeRemoteMediator.Factory,
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
