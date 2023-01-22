package com.doskoch.template.di.modules

import com.doskoch.template.navigation.navigators.SplashFeatureNavigatorImpl
import com.doskoch.template.splash.presentation.navigation.SplashFeatureNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SplashFeatureModule {
    @Binds
    fun navigator(impl: SplashFeatureNavigatorImpl): SplashFeatureNavigator
}
