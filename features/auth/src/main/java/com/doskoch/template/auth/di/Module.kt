package com.doskoch.template.auth.di

import com.doskoch.template.auth.navigation.AuthFeatureNavigator
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@dagger.Module
@InstallIn(SingletonComponent::class)
internal object Module {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface Provider {
        fun navigator(): AuthFeatureNavigator
    }
}
