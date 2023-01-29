package com.doskoch.template.anime.data.di

import com.doskoch.template.anime.data.AnimeLocalRepositoryImpl
import com.doskoch.template.anime.data.AnimeRemoteRepositoryImpl
import com.doskoch.template.anime.domain.AnimeLocalRepository
import com.doskoch.template.anime.domain.AnimeRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AnimeFeatureDataModule {
    @Binds
    fun localRepository(impl: AnimeLocalRepositoryImpl): AnimeLocalRepository

    @Binds
    fun remoteRepository(impl: AnimeRemoteRepositoryImpl): AnimeRemoteRepository
}