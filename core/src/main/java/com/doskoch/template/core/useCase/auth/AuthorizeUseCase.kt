package com.doskoch.template.core.useCase.auth

import com.doskoch.template.core.store.AuthDataStore
import kotlinx.coroutines.delay
import javax.inject.Inject

class AuthorizeUseCase @Inject constructor(private val store: AuthDataStore) {
    suspend fun invoke() {
        store.updateAuthorized(true)
        delay(1_000) // simulate a long-running operation
    }
}
