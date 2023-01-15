package com.doskoch.template.splash.di

import com.doskoch.template.core.useCase.auth.IsAuthorizedUseCase
import com.doskoch.template.splash.MIN_SPLASH_DISPLAY_TIME
import com.doskoch.template.splash.navigation.SplashFeatureNavigator
import com.doskoch.template.splash.screens.splash.SplashViewModel
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@dagger.Module
@InstallIn(ViewModelComponent::class)
internal object Module {

    @Provides
    fun splashViewModel(isAuthorizedUseCase: IsAuthorizedUseCase, navigator: SplashFeatureNavigator) = SplashViewModel(
        minDisplayTime = MIN_SPLASH_DISPLAY_TIME,
        isAuthorizedUseCase = isAuthorizedUseCase,
        navigator = navigator
    )
}
