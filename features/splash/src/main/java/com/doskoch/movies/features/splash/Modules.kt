package com.doskoch.movies.features.splash

import com.doskoch.movies.features.splash.view.SplashFragment
import com.doskoch.movies.features.splash.viewModel.SplashViewModel
import com.extensions.lifecycle.functions.typeSafeViewModelFactory

fun SplashFragment.newModule() = SplashFragment.Module(
    typeSafeViewModelFactory { SplashViewModel(newViewModelModule()) },
    Injector.directions,
    1
)

fun SplashFragment.newViewModelModule() = SplashViewModel.Module(MIN_SPLASH_DISPLAY_TIME)