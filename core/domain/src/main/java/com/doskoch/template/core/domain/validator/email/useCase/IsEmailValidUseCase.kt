package com.doskoch.template.core.domain.validator.email.useCase

import com.doskoch.template.core.domain.validator.email.EmailValidator
import javax.inject.Inject

class IsEmailValidUseCase @Inject constructor(private val validator: EmailValidator) {
    fun invoke(email: String) = validator.isValid(email)
}
