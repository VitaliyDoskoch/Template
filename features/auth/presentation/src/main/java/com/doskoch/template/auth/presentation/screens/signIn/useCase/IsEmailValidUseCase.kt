package com.doskoch.template.auth.presentation.screens.signIn.useCase

import javax.inject.Inject

class IsEmailValidUseCase @Inject constructor() {
    fun invoke(email: String) = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
