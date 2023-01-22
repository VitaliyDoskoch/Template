package com.doskoch.template.auth.presentation.screens.signIn

import android.content.Context
import androidx.lifecycle.ViewModel
import com.doskoch.template.auth.presentation.navigation.AuthFeatureNavigator
import com.doskoch.template.auth.domain.screens.signIn.useCase.IsEmailValidUseCase
import com.doskoch.template.core.R
import com.doskoch.template.core.components.error.CoreError
import com.doskoch.template.core.components.error.GlobalErrorHandler
import com.doskoch.template.core.components.error.toCoreError
import com.doskoch.template.core.ext.launchAction
import com.doskoch.template.core.useCase.auth.AuthorizeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val navigator: AuthFeatureNavigator,
    private val isEmailValidUseCase: IsEmailValidUseCase,
    private val globalErrorHandler: GlobalErrorHandler,
    private val authorizeUseCase: AuthorizeUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<SignInScreenState> = MutableStateFlow(
        SignInScreenState(
            email = "",
            error = null,
            isProceedButtonEnabled = false,
            isLoading = false,
            actions = SignInScreenState.Actions(
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
        override fun localizedMessage(context: Context) = context.getString(R.string.sign_in_screen_error_invalid_email)
    }
}
