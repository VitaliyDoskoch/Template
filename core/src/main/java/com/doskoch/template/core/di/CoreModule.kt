package com.doskoch.template.core.di

import com.doskoch.template.core.useCase.auth.AuthorizeUseCase
import com.doskoch.template.core.useCase.auth.IsAuthorizedUseCase
import com.doskoch.template.core.useCase.auth.LogoutUseCase

object CoreModule {

    fun authorizeUseCase() = AuthorizeUseCase(store = Injector.authDataStore)

    fun isAuthorizedUseCase() = IsAuthorizedUseCase(store = Injector.authDataStore)

    fun logoutUseCase() = LogoutUseCase(store = Injector.authDataStore)
}
