package com.doskoch.template.splash.di

import com.doskoch.template.core.di.CoreModule
import com.doskoch.template.splash.MIN_SPLASH_DISPLAY_TIME
import com.doskoch.template.splash.screens.splash.SplashViewModel

internal object Module {

    fun splashViewModel() = SplashViewModel(
        minDisplayTime = MIN_SPLASH_DISPLAY_TIME,
        isAuthorizedUseCase = CoreModule.isAuthorizedUseCase(),
        navigator = Injector.navigator
    )
}
