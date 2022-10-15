package com.doskoch.template.splash.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doskoch.template.core.useCase.authorization.IsAuthorizedUseCase
import com.doskoch.template.splash.di.SplashFeatureNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val minDisplayTime: Long,
    private val isAuthorizedUseCase: IsAuthorizedUseCase,
    private val navigator: SplashFeatureNavigator
) : ViewModel() {

    init {
        launchTimer()
    }

    private fun launchTimer() = viewModelScope.launch {
        delay(minDisplayTime)

        if (isAuthorizedUseCase.invoke()) {
            navigator.toAnime()
        } else {
            navigator.toAuthorization()
        }
    }
}
