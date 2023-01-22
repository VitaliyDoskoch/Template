package com.doskoch.template.auth.domain.screens.signIn.useCase

import javax.inject.Inject

class IsEmailValidUseCase @Inject constructor(private val validator: EmailValidator) {

    interface EmailValidator {
        fun isValid(email: String): Boolean
    }

    fun invoke(email: String) = validator.isValid(email)
}
