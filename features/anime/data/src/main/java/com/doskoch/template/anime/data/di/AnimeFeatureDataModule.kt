package com.doskoch.template.anime.data.di

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.doskoch.template.anime.data.AnimeDataConverter
import com.doskoch.template.anime.data.AnimeInMemoryRepositoryImpl
import com.doskoch.template.anime.data.AnimeLocalRepositoryImpl
import com.doskoch.template.anime.data.AnimeRemoteRepositoryImpl
import com.doskoch.template.anime.domain.AnimeInMemoryRepository
import com.doskoch.template.anime.domain.AnimeLocalRepository
import com.doskoch.template.anime.domain.AnimeRemoteRepository
import com.doskoch.template.database.schema.anime.DbAnimeDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.map

val PAGING_CONFIG = PagingConfig(pageSize = 10, initialLoadSize = 20)

@Module
@InstallIn(SingletonComponent::class)
interface AnimeFeatureDataModule {
    @Binds
    fun localRepository(impl: AnimeLocalRepositoryImpl): AnimeLocalRepository

    @Binds
    fun remoteRepository(impl: AnimeRemoteRepositoryImpl): AnimeRemoteRepository

    @Binds
    fun inMemoryRepository(impl: AnimeInMemoryRepositoryImpl): AnimeInMemoryRepository

    companion object {
        @Provides
        fun favoritePager(dbAnimeDao: DbAnimeDao) = Pager(
            config = PAGING_CONFIG,
            pagingSourceFactory = dbAnimeDao::pagingSource
        ).flow.map { it.map { AnimeDataConverter().toAnimeItem(it) } }
    }
}
