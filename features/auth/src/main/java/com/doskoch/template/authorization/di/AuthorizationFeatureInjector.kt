package com.doskoch.template.authorization.di

import com.doskoch.template.core.components.kotlin.DestroyableLazy

object AuthorizationFeatureInjector {
    var provider: DestroyableLazy<AuthorizationFeatureComponent>? = null
}

internal val Injector: AuthorizationFeatureComponent
    get() = AuthorizationFeatureInjector.provider!!.value
