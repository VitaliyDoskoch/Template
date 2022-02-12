package com.doskoch.movies.features.splash

import com.doskoch.legacy.android.viewModel.viewModelFactory
import com.doskoch.movies.features.splash.view.SplashFragment
import com.doskoch.movies.features.splash.viewModel.SplashViewModel

fun splashFragmentDependencies() = SplashFragment.Dependencies(
    viewModelFactory { SplashViewModel(splashViewModelDependencies()) },
    Injector.directions,
    1
)

fun splashViewModelDependencies() = SplashViewModel.Dependencies(MIN_SPLASH_DISPLAY_TIME)