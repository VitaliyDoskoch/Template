package com.doskoch.template.di.modules

import androidx.navigation.navOptions
import com.doskoch.template.authorization.di.AuthorizationFeature
import com.doskoch.template.authorization.navigation.AuthorizationFeatureNavigator
import com.doskoch.template.di.AppComponent
import com.doskoch.template.navigation.Node
import com.doskoch.template.repositories.AuthorizationFeatureRepositoryImpl

fun authorizationFeatureModule(component: AppComponent) = object : AuthorizationFeature {

    override val navigator = object : AuthorizationFeatureNavigator() {
        override fun toAnime() = component.mainNavigator.toAnime(
            navOptions { popUpTo(Node.Authorization.route) { inclusive = true } }
        )
    }

    override val globalErrorHandler = component.globalErrorHandler

    override val repository = AuthorizationFeatureRepositoryImpl(
        dataStore = component.authorizationDataStore
    )
}