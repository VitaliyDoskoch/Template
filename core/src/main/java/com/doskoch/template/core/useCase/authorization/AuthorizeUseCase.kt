package com.doskoch.template.core.useCase.authorization

import com.doskoch.template.core.store.AuthorizationDataStore
import kotlinx.coroutines.delay

class AuthorizeUseCase(private val store: AuthorizationDataStore) {
    suspend fun invoke() {
        store.updateAuthorized(true)
        delay(1_000) // simulate a long-running operation
    }
}
