package com.doskoch.template.core.useCase.auth

import com.doskoch.template.core.store.AuthDataStore
import kotlinx.coroutines.delay

class AuthorizeUseCase(private val store: AuthDataStore) {
    suspend fun invoke() {
        store.updateAuthorized(true)
        delay(1_000) // simulate a long-running operation
    }
}
