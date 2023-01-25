package com.doskoch.template.auth.presentation.di

import com.doskoch.template.core.android.ext.entryPoint
import com.doskoch.template.core.kotlin.di.ComponentAccessor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface AuthFeatureInjector {

    companion object {
        @Provides
        fun navigator() = AuthFeatureComponentAccessor.entryPoint<AuthFeatureComponent.EntryPoint>().navigator()
    }
}

object AuthFeatureComponentAccessor : ComponentAccessor<AuthFeatureComponent>()
