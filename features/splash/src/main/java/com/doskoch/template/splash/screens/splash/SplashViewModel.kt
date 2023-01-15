package com.doskoch.template.splash.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doskoch.template.core.useCase.auth.IsAuthorizedUseCase
import com.doskoch.template.splash.navigation.SplashFeatureNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
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
            navigator.toAuth()
        }
    }
}
