package com.doskoch.template.authorization.screens.signIn

import android.content.Context
import androidx.lifecycle.ViewModel
import com.doskoch.template.authorization.navigation.AuthorizationFeatureNavigator
import com.doskoch.template.authorization.screens.signIn.useCase.IsEmailValidUseCase
import com.doskoch.template.core.R
import com.doskoch.template.core.components.error.CoreError
import com.doskoch.template.core.components.error.GlobalErrorHandler
import com.doskoch.template.core.components.error.toCoreError
import com.doskoch.template.core.ext.launchAction
import com.doskoch.template.core.useCase.authorization.AuthorizeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel(
    private val navigator: AuthorizationFeatureNavigator,
    private val isEmailValidUseCase: IsEmailValidUseCase,
    private val globalErrorHandler: GlobalErrorHandler,
    private val authorizeUseCase: AuthorizeUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<SignInState> = MutableStateFlow(
        SignInState(
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
    )
    val state = _state.asStateFlow()

    private fun onNavigateBack() = navigator.navigateUp()

    private fun onUpdateEmail(value: String) = _state.update {
        it.copy(email = value, error = null, isProceedButtonEnabled = value.isNotBlank())
    }

    private fun onProceed() = launchAction(
        action = {
            if (state.value.isLoading) return@launchAction

            if (isEmailValidUseCase.invoke(state.value.email.trim())) {
                _state.update { it.copy(isLoading = true) }
                authorizeUseCase.invoke()
                navigator.toAnime()
            } else {
                _state.update { it.copy(error = InvalidEmail) }
            }
        },
        onError = { globalErrorHandler.handle(it.toCoreError()) }
    )

    object InvalidEmail : CoreError() {
        override fun localizedMessage(context: Context) = context.getString(R.string.error_invalid_email)
    }
}
