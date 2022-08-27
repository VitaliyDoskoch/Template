package com.doskoch.template.authorization.di

import com.doskoch.legacy.kotlin.DestroyableLazy
import com.doskoch.template.authorization.screens.signIn.SignInViewModel
import com.doskoch.template.authorization.screens.signIn.useCase.AuthorizeUseCase
import com.doskoch.template.authorization.screens.signIn.useCase.ValidateEmailUseCase
import com.doskoch.template.authorization.screens.signUp.SignUpViewModel

object AuthorizationFeatureInjector {
    var provider: DestroyableLazy<AuthorizationFeature>? = null
}

private val Injector: AuthorizationFeature
    get() = AuthorizationFeatureInjector.provider!!.value

internal object Module {

    fun signUpViewModel() = SignUpViewModel(
        nestedNavigator = Injector.nestedNavigator,
        globalNavigator = Injector.globalNavigator
    )

    fun signInViewModel() = SignInViewModel(
        nestedNavigator = Injector.nestedNavigator,
        validateEmailUseCase = ValidateEmailUseCase(),
        globalErrorHandler = Injector.globalErrorHandler,
        globalNavigator = Injector.globalNavigator,
        authorizeUseCase = AuthorizeUseCase(repository = Injector.repository)
    )
}