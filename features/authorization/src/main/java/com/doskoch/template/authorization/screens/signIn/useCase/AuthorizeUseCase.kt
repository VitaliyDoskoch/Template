package com.doskoch.template.authorization.screens.signIn.useCase

import com.doskoch.template.authorization.di.AuthorizationFeatureRepository
import kotlinx.coroutines.delay

class AuthorizeUseCase(
    private val repository: AuthorizationFeatureRepository
) {
    suspend fun invoke() {
        repository.authorize()
        delay(2000) //simulate a long-running operation
    }
}