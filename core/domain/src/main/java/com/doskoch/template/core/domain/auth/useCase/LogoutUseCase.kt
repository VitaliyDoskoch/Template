package com.doskoch.template.core.domain.auth.useCase

import com.doskoch.template.core.domain.auth.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend fun invoke() = repository.updateIsAuthorized(false)
}
