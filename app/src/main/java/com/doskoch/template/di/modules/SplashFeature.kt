package com.doskoch.template.di.modules

import androidx.navigation.navOptions
import com.doskoch.template.di.AppComponent
import com.doskoch.template.navigation.MainNavigator
import com.doskoch.template.splash.di.SplashFeatureComponent
import com.doskoch.template.splash.di.SplashFeatureNavigator

fun splashFeatureModule(component: AppComponent) = object : SplashFeatureComponent {
    override val navigator = object : SplashFeatureNavigator {
        val navOptions = navOptions { popUpTo(MainNavigator.startNode.route) { inclusive = true } }

        override fun toAuthorization() = component.navigator.toAuthorization(navOptions)

        override fun toAnime() = component.navigator.toAnime(navOptions)
    }

    override val authorizationDataStore = component.authorizationDataStore
}
