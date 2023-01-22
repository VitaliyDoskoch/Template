package com.doskoch.template.core.domain.validator.email

interface EmailValidator {
    fun isValid(email: String): Boolean
}
