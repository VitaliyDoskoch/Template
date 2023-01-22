package com.doskoch.template.core.data.di

import com.doskoch.template.core.data.repositories.AuthRepositoryImpl
import com.doskoch.template.core.domain.auth.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CoreDataModule {
    @Binds
    fun authRepository(impl: AuthRepositoryImpl): AuthRepository
}