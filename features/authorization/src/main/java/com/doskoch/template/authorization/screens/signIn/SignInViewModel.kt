package com.doskoch.template.authorization.screens.signIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doskoch.template.authorization.navigation.AuthorizationFeatureNavigator
import com.doskoch.template.authorization.screens.signIn.useCase.AuthorizeUseCase
import com.doskoch.template.authorization.screens.signIn.useCase.IsEmailValidUseCase
import com.doskoch.template.core.components.error.CoreError
import com.doskoch.template.core.components.error.GlobalErrorHandler
import com.doskoch.template.core.components.error.toCoreError
import com.doskoch.template.core.functions.perform
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val navigator: AuthorizationFeatureNavigator,
    private val isEmailValidUseCase: IsEmailValidUseCase,
    private val globalErrorHandler: GlobalErrorHandler,
    private val authorizeUseCase: AuthorizeUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(initialState())
    val state = _state.asStateFlow()

    private fun initialState(): SignInState = SignInState(
        email = "",
        error = null,
        isProceedButtonEnabled = false,
        isLoading = false,
        actions = SignInState.Actions(
            onNavigateBack = this::onNavigateBack,
            onUpdateEmail = this::onUpdateEmail,
            onProceed = this::onProceed
        )
    )

    private fun onNavigateBack() = viewModelScope.launch { navigator.navigateUp() }

    private fun onUpdateEmail(value: String) = viewModelScope.launch {
        _state.update { it.copy(email = value, error = null, isProceedButtonEnabled = value.isNotBlank()) }
    }

    private fun onProceed() = perform(
        action = {
            if (state.value.isLoading) return@perform

            if (isEmailValidUseCase.invoke(state.value.email.trim())) {
                _state.update { it.copy(isLoading = true) }
                authorizeUseCase.invoke()
                navigator.toAnime()
            } else {
                _state.update { it.copy(error = CoreError.InvalidEmail()) }
            }
        },
        onError = { globalErrorHandler.handle(it.toCoreError()) }
    )
}