package com.doskoch.template.useCase

import com.doskoch.template.data.store.AuthorizationDataStore

class LogoutUseCaseImpl(
    private val authorizationDataStore: AuthorizationDataStore
) {
    suspend fun invoke() {
        authorizationDataStore.updateAuthorized(false)
    }
}