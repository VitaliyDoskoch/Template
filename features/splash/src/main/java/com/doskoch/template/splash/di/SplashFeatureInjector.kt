package com.doskoch.template.splash.di

import com.doskoch.legacy.kotlin.DestroyableLazy

object SplashFeatureInjector {
    var provider: DestroyableLazy<SplashFeatureComponent>? = null
}

internal val Injector: SplashFeatureComponent
    get() = SplashFeatureInjector.provider!!.value
