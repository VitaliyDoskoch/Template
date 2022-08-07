package com.doskoch.template.splash

import com.doskoch.legacy.kotlin.DestroyableLazy

interface SplashFeature {
    val navigator: SplashFeatureNavigator
}

interface SplashFeatureNavigator {
    fun toAuthorization()
}

object SplashFeatureInjector {
    var provider: DestroyableLazy<SplashFeature>? = null
}

internal val Injector: SplashFeature
    get() = SplashFeatureInjector.provider!!.value

object Module {

    val splashViewModel: SplashViewModel
        get() = SplashViewModel(minDisplayTime = MIN_SPLASH_DISPLAY_TIME, navigator = Injector.navigator)

}
