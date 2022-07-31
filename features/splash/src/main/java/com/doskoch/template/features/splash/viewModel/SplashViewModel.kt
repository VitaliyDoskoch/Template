package com.doskoch.template.features.splash.viewModel

import androidx.lifecycle.ViewModel

class SplashViewModel(private val module: Module) : ViewModel() {

    data class Module(val minDisplayTime: Long)
}
