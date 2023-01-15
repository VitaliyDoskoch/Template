package com.doskoch.template.di.modules

import com.doskoch.template.core.components.navigation.NavigationNode
import com.doskoch.template.navigation.MainNavigator
import com.doskoch.template.navigation.Node
import com.doskoch.template.splash.navigation.SplashFeatureNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SplashFeatureModule {

    @Provides
    fun navigator(navigator: MainNavigator) = object : SplashFeatureNavigator() {
        override val startNode: NavigationNode = Node.Splash

        override fun toAuth() = navigator.toAuthFromSplash()
        override fun toAnime() = navigator.toAnimeFromSplash()
    }
}
