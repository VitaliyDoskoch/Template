package com.doskoch.template.authorization.useCase

class IsEmailValidUseCase {
    fun invoke(email: String) = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}