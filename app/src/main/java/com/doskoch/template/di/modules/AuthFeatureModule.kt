package com.doskoch.template.di.modules

import com.doskoch.template.auth.navigation.AuthFeatureNavigator
import com.doskoch.template.navigation.MainNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthFeatureModule {

    @Provides
    @Singleton
    fun navigator(navigator: MainNavigator) = object : AuthFeatureNavigator() {
        override fun toAnime() = navigator.toAnimeFromAuth()
    }
}
