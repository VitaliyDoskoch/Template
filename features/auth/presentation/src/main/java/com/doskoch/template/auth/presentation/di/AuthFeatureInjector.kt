package com.doskoch.template.auth.presentation.di

import com.doskoch.template.core.android.ext.entryPoint
import com.doskoch.template.core.kotlin.di.ComponentAccessor
import com.doskoch.template.core.kotlin.di.FeatureScoped
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AuthFeatureInjector {

    companion object {
        @Provides
        @FeatureScoped
        fun navigator() = AuthFeatureComponentAccessor.entryPoint<AuthFeatureComponent.EntryPoint>().authFeatureNavigator()
    }
}

object AuthFeatureComponentAccessor : ComponentAccessor<AuthFeatureComponent>()
