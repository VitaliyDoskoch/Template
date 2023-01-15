package com.doskoch.template.auth.screens.signIn.useCase

import javax.inject.Inject

class IsEmailValidUseCase @Inject constructor() {
    fun invoke(email: String) = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
