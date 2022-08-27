package com.doskoch.template.authorization.di

import com.doskoch.legacy.kotlin.DestroyableLazy
import com.doskoch.template.authorization.screens.signIn.SignInViewModel
import com.doskoch.template.authorization.screens.signIn.useCase.AuthorizeUseCase
import com.doskoch.template.authorization.screens.signIn.useCase.ValidateEmailUseCase
import com.doskoch.template.authorization.screens.signUp.SignUpViewModel

object AuthorizationFeatureInjector {
    var provider: DestroyableLazy<AuthorizationFeature>? = null
}

internal val Injector: AuthorizationFeature
    get() = AuthorizationFeatureInjector.provider!!.value

internal object Module {

    fun signUpViewModel() = SignUpViewModel(
        navigator = Injector.navigator
    )

    fun signInViewModel() = SignInViewModel(
        navigator = Injector.navigator,
        validateEmailUseCase = ValidateEmailUseCase(),
        globalErrorHandler = Injector.globalErrorHandler,
        authorizeUseCase = AuthorizeUseCase(repository = Injector.repository)
    )
}