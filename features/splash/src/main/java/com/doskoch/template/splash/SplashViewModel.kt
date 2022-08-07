package com.doskoch.template.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doskoch.template.splash.di.SplashFeatureRepository
import com.doskoch.template.splash.di.SplashFeatureNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val minDisplayTime: Long,
    private val repository: SplashFeatureRepository,
    private val featureNavigator: SplashFeatureNavigator
) : ViewModel() {

    init {
        launchTimer()
    }

    private fun launchTimer() = viewModelScope.launch {
        delay(minDisplayTime)

        if(repository.authorized()) {
            featureNavigator.toAnime()
        } else {
            featureNavigator.toAuthorization()
        }
    }
}
