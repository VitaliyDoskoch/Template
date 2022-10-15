package com.doskoch.template.core.useCase.authorization

import com.doskoch.template.core.store.AuthorizationDataStore
import kotlinx.coroutines.flow.firstOrNull

class IsAuthorizedUseCase(private val store: AuthorizationDataStore) {
    suspend fun invoke() = store.authorized().firstOrNull() ?: false
}