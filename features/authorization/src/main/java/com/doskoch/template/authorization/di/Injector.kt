package com.doskoch.template.authorization.di

import com.doskoch.legacy.kotlin.DestroyableLazy
import com.doskoch.template.authorization.signIn.SignInViewModel
import com.doskoch.template.authorization.signIn.useCase.AuthorizeUseCase
import com.doskoch.template.authorization.signIn.useCase.ValidateEmailUseCase
import com.doskoch.template.authorization.signUp.SignUpViewModel

object AuthorizationFeatureInjector {
    var provider: DestroyableLazy<AuthorizationFeature>? = null
}

internal val Injector: AuthorizationFeature
    get() = AuthorizationFeatureInjector.provider!!.value

object Module {

    val signUpViewModel: SignUpViewModel
        get() = SignUpViewModel(
            nestedNavigator = Injector.nestedNavigator,
            globalNavigator = Injector.globalNavigator
        )

    val signInViewModel: SignInViewModel
        get() = SignInViewModel(
            nestedNavigator = Injector.nestedNavigator,
            validateEmailUseCase = ValidateEmailUseCase(),
            globalErrorHandler = Injector.globalErrorHandler,
            globalNavigator = Injector.globalNavigator,
            authorizeUseCase = AuthorizeUseCase(repository = Injector.repository)
        )
}