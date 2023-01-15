package com.doskoch.template.di.modules

import com.doskoch.template.core.components.navigation.NavigationNode
import com.doskoch.template.di.AppComponent
import com.doskoch.template.navigation.MainNavigator
import com.doskoch.template.navigation.Node
import com.doskoch.template.splash.di.SplashFeatureComponent
import com.doskoch.template.splash.navigation.SplashFeatureNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

fun splashFeatureModule(component: AppComponent) = object : SplashFeatureComponent {
    override val navigator = object : SplashFeatureNavigator() {
        override val startNode: NavigationNode = Node.Splash

        override fun toAuth() = component.navigator.toAuthFromSplash()
        override fun toAnime() = component.navigator.toAnimeFromSplash()
    }
}

@Module
@InstallIn(ViewModelComponent::class)
object SplashFeatureModule {

    @Provides
    fun navigator(navigator: MainNavigator) = object : SplashFeatureNavigator() {
        override val startNode: NavigationNode = Node.Splash

        override fun toAuth() = navigator.toAuthFromSplash()
        override fun toAnime() = navigator.toAnimeFromSplash()
    }
}
