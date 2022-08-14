package com.doskoch.template.di.modules

import androidx.navigation.navOptions
import com.doskoch.template.authorization.di.AuthorizationFeature
import com.doskoch.template.authorization.di.AuthorizationFeatureNavigator
import com.doskoch.template.authorization.AuthorizationNestedNavigator
import com.doskoch.template.di.AppComponent
import com.doskoch.template.navigation.Destinations
import com.doskoch.template.repositories.AuthorizationFeatureRepositoryImpl

fun authorizationFeatureModule(component: AppComponent) = object : AuthorizationFeature {
    override val featureNavigator = object : AuthorizationFeatureNavigator {
        override fun toAnime() = navOptions { popUpTo(Destinations.Authorization.name) { inclusive = true } }
            .let(component.mainNavigator::toAnime)
    }

    override val nestedNavigator = AuthorizationNestedNavigator()

    override val globalErrorHandler = component.globalErrorHandler

    override val repository = AuthorizationFeatureRepositoryImpl(
        dataStore = component.authorizationDataStore
    )
}