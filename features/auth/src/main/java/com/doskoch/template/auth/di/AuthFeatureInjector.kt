package com.doskoch.template.auth.di

import com.doskoch.template.core.components.kotlin.DestroyableLazy
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object AuthFeatureInjector {
    var component: DestroyableLazy<AuthFeatureComponent>? = null

    @Provides
    fun navigator() = EntryPoints.get(component!!.value, AuthFeatureComponent.EntryPoint::class.java).navigator()
}
