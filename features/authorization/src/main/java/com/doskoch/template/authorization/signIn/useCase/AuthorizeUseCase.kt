package com.doskoch.template.authorization.signIn.useCase

import com.doskoch.template.authorization.di.AuthorizationFeatureRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class AuthorizeUseCase(
    private val repository: AuthorizationFeatureRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun invoke() = withContext(dispatcher) {
        repository.authorize()
        delay(2000) //simulate a long-running operation
    }
}