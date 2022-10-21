package com.doskoch.template.authorization.screens.signUp

import androidx.lifecycle.ViewModel
import com.doskoch.template.authorization.navigation.AuthorizationFeatureNavigator

class SignUpViewModel(
    private val navigator: AuthorizationFeatureNavigator
) : ViewModel() {

    fun onSignIn() = navigator.toSignIn()

    fun onSkip() = navigator.toAnime()
}
