package com.doskoch.template.database.di

import com.doskoch.template.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AppDatabaseModule {

    companion object {
        @Provides
        fun dbAnimeDao(appDatabase: AppDatabase) = appDatabase.dbAnimeDao()
    }
}