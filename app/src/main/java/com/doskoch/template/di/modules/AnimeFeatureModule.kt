package com.doskoch.template.di.modules

import com.doskoch.template.anime.di.AnimeFeatureComponent
import com.doskoch.template.anime.di.AnimeFeatureScope
import com.doskoch.template.anime.navigation.AnimeFeatureNavigator
import com.doskoch.template.api.jikan.services.top.responses.GetTopAnimeResponse
import com.doskoch.template.core.components.paging.SimpleInMemoryStorage
import com.doskoch.template.navigation.navigators.AnimeFeatureNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

@Module
@InstallIn(AnimeFeatureComponent::class)
interface AnimeFeatureModule {
    @Binds
    fun navigator(impl: AnimeFeatureNavigatorImpl): AnimeFeatureNavigator

    companion object {
        @Provides
        @AnimeFeatureScope
        fun storage() = SimpleInMemoryStorage<Int, GetTopAnimeResponse.Data>()
    }
}
