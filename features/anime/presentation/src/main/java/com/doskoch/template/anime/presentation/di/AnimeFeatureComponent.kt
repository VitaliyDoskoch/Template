package com.doskoch.template.anime.presentation.di

import com.doskoch.template.anime.domain.model.AnimeItem
import com.doskoch.template.anime.presentation.navigation.AnimeFeatureNavigator
import com.doskoch.template.anime.presentation.screens.details.AnimeDetailsViewModel
import com.doskoch.template.core.android.components.paging.SimpleInMemoryStorage
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
        fun animeFeatureNavigator(): AnimeFeatureNavigator
        fun animeFeatureStorage(): SimpleInMemoryStorage<Int, AnimeItem>
        fun animeDetailsViewModelFactory(): AnimeDetailsViewModel.Factory
    }
}
