package com.doskoch.template.features.splash

import androidx.lifecycle.ViewModel

class SplashViewModel(
    private val minDisplayTime: Long
) : ViewModel() {

    val text = "SplashScreen"
}
