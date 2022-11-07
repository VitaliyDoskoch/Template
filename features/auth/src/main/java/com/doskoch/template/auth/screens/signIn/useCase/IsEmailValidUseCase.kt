package com.doskoch.template.auth.screens.signIn.useCase

class IsEmailValidUseCase {
    fun invoke(email: String) = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
