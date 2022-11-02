package com.doskoch.template.authorization.di

import com.doskoch.legacy.kotlin.DestroyableLazy
import com.doskoch.template.authorization.screens.signIn.SignInViewModel
import com.doskoch.template.authorization.screens.signIn.useCase.IsEmailValidUseCase
import com.doskoch.template.authorization.screens.signUp.SignUpViewModel
import com.doskoch.template.core.useCase.authorization.AuthorizeUseCase

object AuthorizationFeatureInjector {
    var provider: DestroyableLazy<AuthorizationFeatureComponent>? = null
}

internal val Injector: AuthorizationFeatureComponent
    get() = AuthorizationFeatureInjector.provider!!.value
