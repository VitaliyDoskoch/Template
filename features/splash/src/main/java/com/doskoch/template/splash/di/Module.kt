package com.doskoch.template.splash.di

import com.doskoch.template.core.useCase.authorization.IsAuthorizedUseCase
import com.doskoch.template.splash.MIN_SPLASH_DISPLAY_TIME
import com.doskoch.template.splash.screens.splash.SplashViewModel

internal object Module {

    fun splashViewModel() = SplashViewModel(
        minDisplayTime = MIN_SPLASH_DISPLAY_TIME,
        isAuthorizedUseCase = IsAuthorizedUseCase(store = Injector.authorizationDataStore),
        navigator = Injector.navigator
    )
}
