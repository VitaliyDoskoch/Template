package com.doskoch.template.di.modules

import androidx.navigation.navOptions
import com.doskoch.template.authorization.di.AuthorizationFeatureComponent
import com.doskoch.template.authorization.navigation.AuthorizationFeatureNavigator
import com.doskoch.template.di.AppComponent
import com.doskoch.template.navigation.Node

fun authorizationFeatureModule(component: AppComponent) = object : AuthorizationFeatureComponent {

    override val navigator = object : AuthorizationFeatureNavigator() {
        override fun toAnime() = component.mainNavigator.toAnime(
            navOptions { popUpTo(Node.Authorization.route) { inclusive = true } }
        )
    }

    override val globalErrorHandler = component.globalErrorHandler

    override val authorizationDataStore = component.authorizationDataStore
}
