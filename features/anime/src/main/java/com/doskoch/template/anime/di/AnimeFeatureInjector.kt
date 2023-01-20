package com.doskoch.template.anime.di

import com.doskoch.template.core.components.kotlin.DestroyableLazy
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface AnimeFeatureInjector {

    companion object {
        var component: DestroyableLazy<AnimeFeatureComponent>? = null

        @Provides
        fun navigator() = EntryPoints.get(component!!.value, AnimeFeatureComponent.EntryPoint::class.java).navigator()
    }
}
