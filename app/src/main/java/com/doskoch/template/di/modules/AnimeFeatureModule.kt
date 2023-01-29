package com.doskoch.template.di.modules

import com.doskoch.template.anime.domain.model.AnimeItem
import com.doskoch.template.anime.presentation.di.AnimeFeatureComponent
import com.doskoch.template.anime.presentation.di.AnimeFeatureScope
import com.doskoch.template.anime.presentation.navigation.AnimeFeatureNavigator
import com.doskoch.template.core.android.components.paging.SimpleInMemoryStorage
import com.doskoch.template.navigation.navigators.AnimeFeatureNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

@Module
@InstallIn(AnimeFeatureComponent::class)
interface AnimeFeatureModule {
    @Binds
    @AnimeFeatureScope
    fun navigator(impl: AnimeFeatureNavigatorImpl): AnimeFeatureNavigator

    companion object {
        @Provides
        @AnimeFeatureScope
        fun animeFeatureStorage() = SimpleInMemoryStorage<Int, AnimeItem>()
    }
}
