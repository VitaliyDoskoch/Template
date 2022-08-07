package com.doskoch.template.authorization

import com.doskoch.legacy.kotlin.DestroyableLazy
import com.doskoch.template.authorization.signIn.SignInViewModel
import com.doskoch.template.authorization.signIn.ValidateEmailUseCase
import com.doskoch.template.authorization.signUp.SignUpViewModel
import com.doskoch.template.core.error.GlobalErrorHandler

interface AuthorizationFeature {
    val navigator: AuthorizationFeatureNavigator
    val nestedNavigator: AuthorizationNestedNavigator
    val globalErrorHandler: GlobalErrorHandler
}

interface AuthorizationFeatureNavigator {
    fun toAnime()
}

object AuthorizationFeatureInjector {
    var provider: DestroyableLazy<AuthorizationFeature>? = null
}

internal val Injector: AuthorizationFeature
    get() = AuthorizationFeatureInjector.provider!!.value

object Module {

    val signUpViewModel: SignUpViewModel
        get() = SignUpViewModel(navigator = Injector.nestedNavigator)

    val signInViewModel: SignInViewModel
        get() = SignInViewModel(
            navigator = Injector.nestedNavigator,
            validateEmailUseCase = ValidateEmailUseCase(),
            globalErrorHandler = Injector.globalErrorHandler,
            featureNavigator = Injector.navigator
        )
}