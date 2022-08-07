package com.doskoch.template.di.modules

import androidx.navigation.navOptions
import com.doskoch.template.repositories.SplashFeatureRepositoryImpl
import com.doskoch.template.di.AppComponent
import com.doskoch.template.navigation.MainNavigator
import com.doskoch.template.splash.di.SplashFeature
import com.doskoch.template.splash.di.SplashFeatureNavigator

fun splashFeatureModule(component: AppComponent) = object : SplashFeature {
    override val featureNavigator = object : SplashFeatureNavigator {
        val navOptions = navOptions { popUpTo(MainNavigator.startDestination.name) { inclusive = true } }

        override fun toAuthorization() = component.navigator.toAuthorization(navOptions)

        override fun toAnime() = component.navigator.toAnime(navOptions)
    }

    override val repository = SplashFeatureRepositoryImpl(
        dataStore = component.authorizationDataStore
    )
}