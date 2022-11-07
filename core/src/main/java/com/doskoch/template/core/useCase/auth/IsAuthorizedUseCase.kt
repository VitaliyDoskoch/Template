package com.doskoch.template.core.useCase.auth

import com.doskoch.template.core.store.AuthDataStore
import kotlinx.coroutines.flow.firstOrNull

class IsAuthorizedUseCase(private val store: AuthDataStore) {
    suspend fun invoke() = store.authorized().firstOrNull() ?: false
}
