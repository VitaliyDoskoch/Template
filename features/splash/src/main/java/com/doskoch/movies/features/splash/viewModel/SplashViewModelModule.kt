package com.doskoch.movies.features.splash.viewModel

import com.doskoch.movies.features.splash.config.MIN_SPLASH_DISPLAY_TIME

class SplashViewModelModule(
    val minDisplayTime: Long
) {
    companion object {
        fun create(): SplashViewModelModule {
            return SplashViewModelModule(
                minDisplayTime = MIN_SPLASH_DISPLAY_TIME
            )
        }
    }
}