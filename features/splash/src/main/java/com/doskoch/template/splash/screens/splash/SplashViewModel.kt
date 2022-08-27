package com.doskoch.template.splash.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doskoch.template.splash.di.SplashFeatureNavigator
import com.doskoch.template.splash.di.SplashFeatureRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val minDisplayTime: Long,
    private val repository: SplashFeatureRepository,
    private val navigator: SplashFeatureNavigator
) : ViewModel() {

    init {
        launchTimer()
    }

    private fun launchTimer() = viewModelScope.launch {
        delay(minDisplayTime)

        if (repository.authorized()) {
            navigator.toAnime()
        } else {
            navigator.toAuthorization()
        }
    }
}
