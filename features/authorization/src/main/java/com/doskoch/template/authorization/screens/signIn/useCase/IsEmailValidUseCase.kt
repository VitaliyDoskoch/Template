package com.doskoch.template.authorization.screens.signIn.useCase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IsEmailValidUseCase(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend fun invoke(email: String) = withContext(dispatcher){
        android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}