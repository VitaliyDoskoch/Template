package com.doskoch.template.authorization.di

import com.doskoch.legacy.kotlin.DestroyableLazy
import com.doskoch.template.authorization.screens.signIn.SignInViewModel
import com.doskoch.template.authorization.useCase.IsEmailValidUseCase
import com.doskoch.template.authorization.screens.signUp.SignUpViewModel
import com.doskoch.template.core.useCase.authorization.AuthorizeUseCase

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
        isEmailValidUseCase = IsEmailValidUseCase(),
        globalErrorHandler = Injector.globalErrorHandler,
        authorizeUseCase = AuthorizeUseCase(store = Injector.authorizationDataStore)
    )
}