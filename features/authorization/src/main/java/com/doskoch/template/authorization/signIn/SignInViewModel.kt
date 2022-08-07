package com.doskoch.template.authorization.signIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doskoch.template.authorization.AuthorizationFeatureNavigator
import com.doskoch.template.authorization.AuthorizationNavigator
import com.doskoch.template.core.error.CoreError
import com.doskoch.template.core.error.GlobalErrorHandler
import com.doskoch.template.core.error.toCoreError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class SignInViewModel(
    private val navigator: AuthorizationNavigator,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val globalErrorHandler: GlobalErrorHandler,
    private val featureNavigator: AuthorizationFeatureNavigator
) : ViewModel() {

    private val _state = MutableStateFlow(State.default(this))
    val state = _state.asStateFlow()

    private fun onNavigateBack() = viewModelScope.launch { navigator.navigateUp() }

    private fun onUpdateEmail(value: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                email = value,
                error = null,
                isProceedButtonEnabled = value.isNotBlank()
            )
        }
    }

    private fun onProceed() = viewModelScope.launch {
        try {
            if(validateEmailUseCase.invoke(state.value.email.trim())) {
                featureNavigator.toMain()
            } else {
                _state.update { it.copy(error = CoreError.InvalidEmail()) }
            }
        } catch (t: Throwable) {
            Timber.e(t)
            globalErrorHandler.showError(CoreError.Unknown())
        }
    }

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