package com.doskoch.template.authorization.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doskoch.template.authorization.navigation.AuthorizationNestedNavigator
import com.doskoch.template.authorization.di.AuthorizationFeatureGlobalNavigator
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val nestedNavigator: AuthorizationNestedNavigator,
    private val globalNavigator: AuthorizationFeatureGlobalNavigator
) : ViewModel() {

    fun onSignIn() = viewModelScope.launch { nestedNavigator.toSignIn() }

    fun onSkip() = viewModelScope.launch { globalNavigator.toAnime() }
}