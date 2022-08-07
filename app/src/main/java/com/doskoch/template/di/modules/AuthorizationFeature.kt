package com.doskoch.template.di.modules

import androidx.navigation.navOptions
import com.doskoch.template.authorization.AuthorizationFeature
import com.doskoch.template.authorization.AuthorizationFeatureNavigator
import com.doskoch.template.authorization.AuthorizationNestedNavigator
import com.doskoch.template.di.AppComponent
import com.doskoch.template.navigation.Destinations

fun authorizationFeatureModule(component: AppComponent) = object : AuthorizationFeature {
    override val navigator = object : AuthorizationFeatureNavigator {
        override fun toAnime() = navOptions { popUpTo(Destinations.Authorization.name) { inclusive = true } }
            .let(component.navigator::toAnime)
    }

    override val nestedNavigator = AuthorizationNestedNavigator()

    override val globalErrorHandler = component.globalErrorHandlerHolder.handler
}