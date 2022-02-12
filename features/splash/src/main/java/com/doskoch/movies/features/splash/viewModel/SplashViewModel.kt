package com.doskoch.movies.features.splash.viewModel

import androidx.lifecycle.ViewModel

class SplashViewModel(private val dependencies: Dependencies) : ViewModel() {

    data class Dependencies(val minDisplayTime: Long)

}