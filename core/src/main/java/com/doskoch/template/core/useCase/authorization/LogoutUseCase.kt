package com.doskoch.template.core.useCase.authorization

import com.doskoch.template.core.store.AuthorizationDataStore

class LogoutUseCase(private val store: AuthorizationDataStore) {
    suspend fun invoke() = store.updateAuthorized(false)
}
