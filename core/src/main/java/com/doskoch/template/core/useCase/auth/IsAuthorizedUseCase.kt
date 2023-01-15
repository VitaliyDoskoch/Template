package com.doskoch.template.core.useCase.auth

import com.doskoch.template.core.store.AuthDataStore
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class IsAuthorizedUseCase @Inject constructor(private val store: AuthDataStore) {
    suspend fun invoke() = store.authorized().firstOrNull() ?: false
}
