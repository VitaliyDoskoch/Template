package com.doskoch.template.core.di

import com.doskoch.template.core.useCase.authorization.AuthorizeUseCase
import com.doskoch.template.core.useCase.authorization.IsAuthorizedUseCase
import com.doskoch.template.core.useCase.authorization.LogoutUseCase

object CoreModule {

    fun authorizeUseCase() = AuthorizeUseCase(store = Injector.authorizationDataStore)

    fun isAuthorizedUseCase() = IsAuthorizedUseCase(store = Injector.authorizationDataStore)

    fun logoutUseCase() = LogoutUseCase(store = Injector.authorizationDataStore)
}