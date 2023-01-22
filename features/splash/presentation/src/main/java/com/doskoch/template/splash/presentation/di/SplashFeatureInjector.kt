package com.doskoch.template.splash.presentation.di

import com.doskoch.template.core.useCase.auth.IsAuthorizedUseCase
import com.doskoch.template.splash.presentation.MIN_SPLASH_DISPLAY_TIME
import com.doskoch.template.splash.presentation.navigation.SplashFeatureNavigator
import com.doskoch.template.splash.presentation.screens.splash.SplashViewModel
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@dagger.Module
@InstallIn(ActivityRetainedComponent::class)
interface SplashFeatureInjector {

    companion object {
        @Provides
        fun splashViewModel(isAuthorizedUseCase: IsAuthorizedUseCase, navigator: SplashFeatureNavigator) = SplashViewModel(
            minDisplayTime = MIN_SPLASH_DISPLAY_TIME,
            isAuthorizedUseCase = isAuthorizedUseCase,
            navigator = navigator
        )
    }
}
