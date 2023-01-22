package com.doskoch.template.core.data.validator

import com.doskoch.template.core.domain.validator.email.EmailValidator
import javax.inject.Inject

class EmailValidatorImpl @Inject constructor() : EmailValidator {
    override fun isValid(email: String) = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
