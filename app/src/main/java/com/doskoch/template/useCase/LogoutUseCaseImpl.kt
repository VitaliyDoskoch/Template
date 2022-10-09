package com.doskoch.template.useCase

import com.doskoch.template.core.components.useCase.LogoutUseCase
import com.doskoch.template.data.store.AuthorizationDataStore

class LogoutUseCaseImpl(
    private val authorizationDataStore: AuthorizationDataStore
) : LogoutUseCase {
    override suspend fun invoke() {
        authorizationDataStore.updateAuthorized(false)
    }
}