package com.doskoch.template.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doskoch.template.splash.di.SplashFeatureDataSource
import com.doskoch.template.splash.di.SplashFeatureNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val minDisplayTime: Long,
    private val dataSource: SplashFeatureDataSource,
    private val navigator: SplashFeatureNavigator
) : ViewModel() {

    init {
        launchTimer()
    }

    private fun launchTimer() = viewModelScope.launch {
        delay(minDisplayTime)

        if(dataSource.authorized()) {
            navigator.toAnime()
        } else {
            navigator.toAuthorization()
        }
    }
}
