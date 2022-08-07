package com.doskoch.template.authorization.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doskoch.template.authorization.AuthorizationNavigator
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val navigator: AuthorizationNavigator
) : ViewModel() {

    fun onSignIn() = viewModelScope.launch { navigator.toSignIn() }

    fun onSkip() = viewModelScope.launch { /*TODO*/ }
}