package com.doskoch.template.authorization.signIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doskoch.template.authorization.AuthorizationNestedNavigator
import com.doskoch.template.authorization.di.AuthorizationFeatureNavigator
import com.doskoch.template.authorization.signIn.useCase.AuthorizeUseCase
import com.doskoch.template.authorization.signIn.useCase.ValidateEmailUseCase
import com.doskoch.template.core.components.error.CoreError
import com.doskoch.template.core.components.error.GlobalErrorHandler
import com.doskoch.template.core.components.error.toCoreError
import com.doskoch.template.core.functions.perform
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val nestedNavigator: AuthorizationNestedNavigator,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val globalErrorHandler: GlobalErrorHandler,
    private val featureNavigator: AuthorizationFeatureNavigator,
    private val authorizeUseCase: AuthorizeUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(State.default(this))
    val state = _state.asStateFlow()

    private fun onNavigateBack() = viewModelScope.launch { nestedNavigator.navigateUp() }

    private fun onUpdateEmail(value: String) = viewModelScope.launch {
        _state.update { it.copy(email = value, error = null, isProceedButtonEnabled = value.isNotBlank()) }
    }

    private fun onProceed() = perform(
        action = {
            if (state.value.isLoading) return@perform

            if (validateEmailUseCase.invoke(state.value.email.trim())) {
                _state.update { it.copy(isLoading = true) }

                authorizeUseCase.invoke()

                featureNavigator.toAnime()
            } else {
                _state.update { it.copy(error = CoreError.InvalidEmail()) }
            }
        },
        onError = { globalErrorHandler.handle(it.toCoreError()) }
    )

    data class State(
        val email: String,
        val error: CoreError?,
        val isProceedButtonEnabled: Boolean,
        val isLoading: Boolean,
        val actions: Actions
    ) {
        companion object {
            fun default(vm: SignInViewModel): State = State(
                email = "",
                error = null,
                isProceedButtonEnabled = false,
                isLoading = false,
                actions = Actions(
                    onNavigateBack = vm::onNavigateBack,
                    onUpdateEmail = vm::onUpdateEmail,
                    onProceed = vm::onProceed
                )
            )
        }
    }

    data class Actions(
        val onNavigateBack: () -> Unit,
        val onUpdateEmail: (String) -> Unit,
        val onProceed: () -> Unit
    )
}