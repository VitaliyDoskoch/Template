package com.doskoch.template.auth.presentation.di

import com.doskoch.template.core.kotlin.di.ComponentAccessor
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface AuthFeatureInjector {

    companion object {
        @Provides
        fun navigator() = EntryPoints.get(AuthFeatureComponentAccessor.get(), AuthFeatureComponent.EntryPoint::class.java).navigator()
    }
}

object AuthFeatureComponentAccessor : ComponentAccessor<AuthFeatureComponent>()
