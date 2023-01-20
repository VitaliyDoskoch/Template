package com.doskoch.template.anime.di

import com.doskoch.template.anime.navigation.AnimeFeatureNavigator
import com.doskoch.template.anime.screens.details.AnimeDetailsViewModel
import dagger.hilt.DefineComponent
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Scope

@Scope
@MustBeDocumented
@Retention(value = AnnotationRetention.RUNTIME)
annotation class AnimeFeatureScope

@AnimeFeatureScope
@DefineComponent(parent = SingletonComponent::class)
interface AnimeFeatureComponent {

    @DefineComponent.Builder
    interface Builder {
        fun build(): AnimeFeatureComponent
    }

    @dagger.hilt.EntryPoint
    @InstallIn(AnimeFeatureComponent::class)
    interface EntryPoint {
        fun navigator(): AnimeFeatureNavigator
        fun animeDetailsViewModelFactory(): AnimeDetailsViewModel.Factory
    }
}
