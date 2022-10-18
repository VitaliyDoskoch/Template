package com.doskoch.template.authorization.screens.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doskoch.template.authorization.navigation.AuthorizationFeatureNavigator
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val navigator: AuthorizationFeatureNavigator
) : ViewModel() {

    fun onSignIn() = navigator.toSignIn()

    fun onSkip() = navigator.toAnime()
}