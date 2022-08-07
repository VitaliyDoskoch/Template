package com.doskoch.template.authorization

import com.doskoch.legacy.kotlin.DestroyableLazy
import com.doskoch.template.authorization.signUp.SignUpViewModel

interface AuthorizationFeature {
    val navigator: AuthorizationFeatureNavigator
    val innerNavigator: AuthorizationNavigator
}

interface AuthorizationFeatureNavigator {

}

object AuthorizationFeatureInjector {
    var provider: DestroyableLazy<AuthorizationFeature>? = null
}

internal val Injector: AuthorizationFeature
    get() = AuthorizationFeatureInjector.provider!!.value

object Module {

    val signUpViewModel: SignUpViewModel
        get() = SignUpViewModel(Injector.innerNavigator)
}