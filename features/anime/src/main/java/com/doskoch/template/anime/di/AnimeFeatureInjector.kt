package com.doskoch.template.anime.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import com.doskoch.template.anime.PAGING_CONFIG
import com.doskoch.template.anime.screens.top.TopAnimeRemoteMediator
import com.doskoch.template.anime.screens.top.TopAnimeViewModel
import com.doskoch.template.api.jikan.services.top.responses.GetTopAnimeResponse
import com.doskoch.template.core.components.kotlin.DestroyableLazy
import com.doskoch.template.core.components.paging.SimpleInMemoryStorage
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

        @Provides
        fun navigator() = EntryPoints.get(component!!.value, AnimeFeatureComponent.EntryPoint::class.java).navigator()

        @OptIn(ExperimentalPagingApi::class)
        @Provides
        fun pagerFactory(
            remoteMediatorFactory: TopAnimeRemoteMediator.Factory,
            storage: SimpleInMemoryStorage<Int, GetTopAnimeResponse.Data>
        ) = TopAnimeViewModel.PagerFactory {
            Pager(
                config = PAGING_CONFIG,
                remoteMediator = remoteMediatorFactory.create(),
                pagingSourceFactory = storage::SimplePagingSource
            )
        }

        @Provides
        fun pager(dbAnimeDao: DbAnimeDao) = Pager(
            config = PAGING_CONFIG,
            pagingSourceFactory = dbAnimeDao::pagingSource
        )

        fun animeDetailsViewModelFactory() = EntryPoints.get(component!!.value, AnimeFeatureComponent.EntryPoint::class.java).animeDetailsViewModelFactory()
    }
}
