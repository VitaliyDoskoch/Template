package com.doskoch.template.features.splash

import com.doskoch.legacy.kotlin.DestroyableLazy

interface SplashFeature {
    val navigator: SplashNavigator
}

interface SplashNavigator {
    fun toSignUp()
}

object SplashFeatureInjector {
    var provider: DestroyableLazy<SplashFeature>? = null
}

internal val Injector
    get() = SplashFeatureInjector.provider!!.value

object Module {

    val splashViewModel: SplashViewModel
        get() = SplashViewModel(minDisplayTime = MIN_SPLASH_DISPLAY_TIME, navigator = Injector.navigator)

}
