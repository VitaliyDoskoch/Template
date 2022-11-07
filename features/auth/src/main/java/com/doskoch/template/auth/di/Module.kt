package com.doskoch.template.auth.di

import com.doskoch.template.auth.screens.signIn.SignInViewModel
import com.doskoch.template.auth.screens.signIn.useCase.IsEmailValidUseCase
import com.doskoch.template.auth.screens.signUp.SignUpViewModel
import com.doskoch.template.core.di.CoreModule

internal object Module {

    fun signUpViewModel() = SignUpViewModel(
        navigator = Injector.navigator
    )

    fun signInViewModel() = SignInViewModel(
        navigator = Injector.navigator,
        isEmailValidUseCase = IsEmailValidUseCase(),
        globalErrorHandler = Injector.globalErrorHandler,
        authorizeUseCase = CoreModule.authorizeUseCase()
    )
}
