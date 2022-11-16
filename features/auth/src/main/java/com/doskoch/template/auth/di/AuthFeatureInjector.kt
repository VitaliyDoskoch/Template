package com.doskoch.template.auth.di

import com.doskoch.template.core.components.kotlin.DestroyableLazy

object AuthFeatureInjector {
    var component: DestroyableLazy<AuthFeatureComponent>? = null
}

internal val Injector: AuthFeatureComponent
    get() = AuthFeatureInjector.component!!.value
