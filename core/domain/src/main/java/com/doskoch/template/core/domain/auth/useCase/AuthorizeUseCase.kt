package com.doskoch.template.core.domain.auth.useCase

import com.doskoch.template.core.domain.auth.AuthRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class AuthorizeUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend fun invoke() {
        repository.updateIsAuthorized(true)
        delay(1_000) // simulate a long-running operation
    }
}
