package com.doskoch.template.features.splash

import androidx.lifecycle.ViewModel

class SplashViewModel(private val module: Module) : ViewModel() {

    data class Module(val minDisplayTime: Long)
}
