package com.doskoch.template.splash.di

import com.doskoch.legacy.kotlin.DestroyableLazy
import com.doskoch.template.splash.MIN_SPLASH_DISPLAY_TIME
import com.doskoch.template.splash.SplashViewModel

object SplashFeatureInjector {
    var provider: DestroyableLazy<SplashFeature>? = null
}

internal val Injector: SplashFeature
    get() = SplashFeatureInjector.provider!!.value

object Module {

    val splashViewModel: SplashViewModel
        get() = SplashViewModel(
            minDisplayTime = MIN_SPLASH_DISPLAY_TIME,
            repository = Injector.repository,
            featureNavigator = Injector.featureNavigator
        )

}