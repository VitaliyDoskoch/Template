package com.doskoch.template.auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object InternalAuthFeatureModule {

    @Provides
    fun navigator() = EntryPoints.get(authFeatureComponentHolder!!.value, AuthFeatureComponent.EntryPoint::class.java).navigator()
}