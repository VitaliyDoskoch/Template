package com.doskoch.template.auth.screens.signUp

import androidx.lifecycle.ViewModel
import com.doskoch.template.auth.navigation.AuthFeatureNavigator

class SignUpViewModel(
    private val navigator: AuthFeatureNavigator
) : ViewModel() {

    fun onSignIn() = navigator.toSignIn()

    fun onSkip() = navigator.toAnime()
}
