package com.doskoch.template.core.useCase.auth

import com.doskoch.template.core.store.AuthDataStore

class LogoutUseCase(private val store: AuthDataStore) {
    suspend fun invoke() = store.updateAuthorized(false)
}
