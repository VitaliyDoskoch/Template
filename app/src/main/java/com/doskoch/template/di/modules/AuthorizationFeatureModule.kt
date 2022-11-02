package com.doskoch.template.di.modules

import com.doskoch.template.authorization.di.AuthorizationFeatureComponent
import com.doskoch.template.authorization.navigation.AuthorizationFeatureNavigator
import com.doskoch.template.di.AppComponent

fun authorizationFeatureModule(component: AppComponent) = object : AuthorizationFeatureComponent {

    override val navigator = object : AuthorizationFeatureNavigator() {
        override fun toAnime() = component.navigator.toAnimeFromAuthorization()
    }

    override val globalErrorHandler = component.globalErrorHandler

    override val authorizationDataStore = component.authorizationDataStore
}
