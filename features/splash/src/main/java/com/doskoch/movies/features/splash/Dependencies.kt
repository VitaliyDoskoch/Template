package com.doskoch.movies.features.splash

import com.doskoch.movies.features.splash.view.SplashFragment
import com.doskoch.movies.features.splash.viewModel.SplashViewModel
import com.extensions.lifecycle.functions.typeSafeViewModelFactory

fun splashFragmentDependencies() = SplashFragment.Dependencies(
    typeSafeViewModelFactory { SplashViewModel(splashViewModelDependencies()) },
    Injector.directions,
    1
)

fun splashViewModelDependencies() = SplashViewModel.Dependencies(MIN_SPLASH_DISPLAY_TIME)