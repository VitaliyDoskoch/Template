package com.doskoch.template.authorization.di

import com.doskoch.template.authorization.screens.signIn.SignInViewModel
import com.doskoch.template.authorization.screens.signIn.useCase.IsEmailValidUseCase
import com.doskoch.template.authorization.screens.signUp.SignUpViewModel
import com.doskoch.template.core.di.CoreModule
import com.doskoch.template.core.useCase.authorization.AuthorizeUseCase

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
