package com.doskoch.template.di.modules

import com.doskoch.template.anime.di.AnimeFeatureComponent
import com.doskoch.template.anime.navigation.AnimeFeatureNavigator
import com.doskoch.template.navigation.navigators.AnimeFeatureNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(AnimeFeatureComponent::class)
interface AnimeFeatureModule {
    @Binds
    fun navigator(impl: AnimeFeatureNavigatorImpl): AnimeFeatureNavigator
}