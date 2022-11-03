package com.doskoch.template.splash.di

import com.doskoch.template.core.components.kotlin.DestroyableLazy

object SplashFeatureInjector {
    var provider: DestroyableLazy<SplashFeatureComponent>? = null
}

internal val Injector: SplashFeatureComponent
    get() = SplashFeatureInjector.provider!!.value
