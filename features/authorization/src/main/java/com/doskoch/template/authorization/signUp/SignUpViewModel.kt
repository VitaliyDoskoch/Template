package com.doskoch.template.authorization.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doskoch.template.authorization.AuthorizationNestedNavigator
import com.doskoch.template.authorization.di.AuthorizationFeatureNavigator
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val nestedNavigator: AuthorizationNestedNavigator,
    private val featureNavigator: AuthorizationFeatureNavigator
) : ViewModel() {

    fun onSignIn() = viewModelScope.launch { nestedNavigator.toSignIn() }

    fun onSkip() = viewModelScope.launch { featureNavigator.toAnime() }
}