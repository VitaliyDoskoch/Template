package com.doskoch.template.features.splash

import com.doskoch.legacy.android.viewModel.viewModelFactory
import com.doskoch.legacy.kotlin.DestroyableLazy

interface SplashFeature

object SplashFeatureInjector {
    var provider: DestroyableLazy<SplashFeature>? = null
}

internal val Injector
    get() = SplashFeatureInjector.provider!!.value

object Modules {

    fun splashFragment() = SplashFragment.Module(
        viewModelFactory = viewModelFactory { SplashViewModel(module = splashViewModel()) },
        versionCode = 1
    )

    fun splashViewModel() = SplashViewModel.Module(MIN_SPLASH_DISPLAY_TIME)
}
