package com.doskoch.template.di.modules

import androidx.navigation.navOptions
import com.doskoch.template.repositories.SplashFeatureRepositoryImpl
import com.doskoch.template.di.AppComponent
import com.doskoch.template.navigation.MainNavigator
import com.doskoch.template.splash.di.SplashFeature
import com.doskoch.template.splash.di.SplashFeatureGlobalNavigator

fun splashFeatureModule(component: AppComponent) = object : SplashFeature {
    override val globalNavigator = object : SplashFeatureGlobalNavigator {
        val navOptions = navOptions { popUpTo(MainNavigator.startDestination.name) { inclusive = true } }

        override fun toAuthorization() = component.mainNavigator.toAuthorization(navOptions)

        override fun toAnime() = component.mainNavigator.toAnime(navOptions)
    }

    override val repository = SplashFeatureRepositoryImpl(
        dataStore = component.authorizationDataStore
    )
}