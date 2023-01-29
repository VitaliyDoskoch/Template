package com.doskoch.template.anime.data.di

import com.doskoch.template.anime.data.AnimeLocalRepositoryImpl
import com.doskoch.template.anime.domain.AnimeLocalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AnimeFeatureDataModule {
    @Binds
    fun localRepository(impl: AnimeLocalRepositoryImpl): AnimeLocalRepository
}