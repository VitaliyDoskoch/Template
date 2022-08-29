package com.doskoch.template.di.modules

import androidx.navigation.navOptions
import com.doskoch.template.repositories.SplashFeatureRepositoryImpl
import com.doskoch.template.di.AppComponent
import com.doskoch.template.navigation.MainNavigator
import com.doskoch.template.splash.di.SplashFeature
import com.doskoch.template.splash.di.SplashFeatureNavigator

fun splashFeatureModule(component: AppComponent) = object : SplashFeature {
    override val navigator = object : SplashFeatureNavigator {
        val navOptions = navOptions { popUpTo(MainNavigator.startDestination.route) { inclusive = true } }

        override fun toAuthorization() = component.mainNavigator.toAuthorization(navOptions)

        override fun toAnime() = component.mainNavigator.toAnime(navOptions)
    }

    override val repository = SplashFeatureRepositoryImpl(
        dataStore = component.authorizationDataStore
    )
}