package com.extensions.retrofit.components.validator

interface SelfValidator {
    fun validate(): ValidationResult
}

inline fun <reified T : SelfValidator> Collection<T>.validate(): ValidationResult {
    val results = map { it.validate() }

    return ValidationResult(
        T::class to results.flatMap { it.fields.second }.toSet(),
        results.mapNotNull { it.nested }.flatten().toSet()
    )
}