package com.doskoch.template.authorization.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doskoch.template.authorization.AuthorizationNestedNavigator
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val navigator: AuthorizationNestedNavigator
) : ViewModel() {

    fun onSignIn() = viewModelScope.launch { navigator.toSignIn() }

    fun onSkip() = viewModelScope.launch { /*TODO*/ }
}