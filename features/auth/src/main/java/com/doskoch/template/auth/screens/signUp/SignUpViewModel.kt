package com.doskoch.template.auth.screens.signUp

import androidx.lifecycle.ViewModel
import com.doskoch.template.auth.di.Bridged
import com.doskoch.template.auth.navigation.AuthFeatureNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    @Bridged private val navigator: AuthFeatureNavigator
) : ViewModel() {

    fun onSignIn() = navigator.toSignIn()

    fun onSkip() = navigator.toAnime()
}
