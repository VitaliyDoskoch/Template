package com.doskoch.template.di.modules

import com.doskoch.template.core.components.navigation.NavigationNode
import com.doskoch.template.di.AppComponent
import com.doskoch.template.navigation.Node
import com.doskoch.template.splash.di.SplashFeatureComponent
import com.doskoch.template.splash.navigation.SplashFeatureNavigator

fun splashFeatureModule(component: AppComponent) = object : SplashFeatureComponent {
    override val navigator = object : SplashFeatureNavigator() {
        override val startNode: NavigationNode = Node.Splash

        override fun toAuthorization() = component.navigator.toAuthorization()
        override fun toAnime() = component.navigator.toAnimeFromSplash()
    }
}
