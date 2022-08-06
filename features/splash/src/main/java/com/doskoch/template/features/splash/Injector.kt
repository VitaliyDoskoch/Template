package com.doskoch.template.features.splash

import com.doskoch.legacy.kotlin.DestroyableLazy

interface SplashFeature

object SplashFeatureInjector {
    var provider: DestroyableLazy<SplashFeature>? = null
}

internal val Injector
    get() = SplashFeatureInjector.provider!!.value

object Module {

    val splashViewModel: SplashViewModel
        get() = SplashViewModel(MIN_SPLASH_DISPLAY_TIME)

}
