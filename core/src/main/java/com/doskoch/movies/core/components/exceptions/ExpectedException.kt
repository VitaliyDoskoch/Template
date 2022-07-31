package com.doskoch.movies.core.components.exceptions

class ExpectedException(
    val errorCode: ErrorCode,
    originalCause: Throwable? = null
) : Exception(originalCause) {

    enum class ErrorCode {
        OPERATION_IS_CANCELED,
        FAILED_TO_LOAD_DATA,
        FAILED_TO_SAVE_CHANGES
    }

    override val message: String? = "\nerrorCode: $errorCode"
}
