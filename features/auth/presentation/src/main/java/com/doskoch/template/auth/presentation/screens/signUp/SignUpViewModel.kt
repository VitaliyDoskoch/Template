package com.doskoch.template.auth.presentation.screens.signUp

import androidx.lifecycle.ViewModel
import com.doskoch.template.auth.presentation.navigation.AuthFeatureNavigator
import com.doskoch.template.core.kotlin.di.FeatureScoped
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    @FeatureScoped private val navigator: AuthFeatureNavigator
) : ViewModel() {

    fun onSignIn() = navigator.toSignIn()

    fun onSkip() = navigator.toAnime()
}
