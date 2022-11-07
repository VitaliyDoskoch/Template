package com.doskoch.template.di.modules

import com.doskoch.template.auth.di.AuthFeatureComponent
import com.doskoch.template.auth.navigation.AuthFeatureNavigator
import com.doskoch.template.di.AppComponent

fun authFeatureModule(component: AppComponent) = object : AuthFeatureComponent {
    override val navigator = object : AuthFeatureNavigator() {
        override fun toAnime() = component.navigator.toAnimeFromAuth()
    }

    override val globalErrorHandler = component.globalErrorHandler
}
