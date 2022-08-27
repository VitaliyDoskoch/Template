package com.doskoch.template.splash.di

import com.doskoch.legacy.kotlin.DestroyableLazy
import com.doskoch.template.splash.MIN_SPLASH_DISPLAY_TIME
import com.doskoch.template.splash.screens.splash.SplashViewModel

object SplashFeatureInjector {
    var provider: DestroyableLazy<SplashFeature>? = null
}

private val Injector: SplashFeature
    get() = SplashFeatureInjector.provider!!.value

internal object Module {

    fun splashViewModel() = SplashViewModel(
        minDisplayTime = MIN_SPLASH_DISPLAY_TIME,
        repository = Injector.repository,
        globalNavigator = Injector.globalNavigator
    )

}