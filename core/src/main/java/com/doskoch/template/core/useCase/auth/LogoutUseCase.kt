package com.doskoch.template.core.useCase.auth

import com.doskoch.template.core.store.AuthDataStore
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val store: AuthDataStore) {
    suspend fun invoke() = store.updateAuthorized(false)
}
