package com.doskoch.movies.core.functions

import androidx.annotation.StringRes
import com.doskoch.api.the_movie_db.HTTP
import com.doskoch.api.the_movie_db.INVALID_RESPONSE
import com.doskoch.api.the_movie_db.NO_INTERNET
import com.doskoch.api.the_movie_db.TIMEOUT
import com.doskoch.api.the_movie_db.UNKNOWN
import com.doskoch.api.the_movie_db.UNKNOWN_HOST
import com.doskoch.movies.core.R
import com.doskoch.movies.core.components.exceptions.ExpectedException
import com.doskoch.movies.core.components.exceptions.ExpectedException.ErrorCode
import com.doskoch.api.the_movie_db.components.exceptions.NetworkException
import java.util.concurrent.TimeoutException

/**
 * Retrieves string resource id of error message for [Throwable]. Returns
 * R.string.error_unknown, if there is no corresponding localized error message.
 */
@StringRes
fun Throwable.localizedErrorMessage(): Int {
    return when (this) {
        is ExpectedException -> localizedErrorMessage()
        is NetworkException -> localizedErrorMessage()
        is TimeoutException -> R.string.error_timeout
        is InterruptedException -> R.string.error_operation_is_canceled
        else -> R.string.error_unknown
    }
}

/**
 * Retrieves string resource id of error message for passed [ExpectedException]. Returns
 * R.string.error_unknown, if there is no corresponding localized error message.
 */
@StringRes
fun ExpectedException.localizedErrorMessage(): Int {
    return when (this.errorCode) {
        ErrorCode.OPERATION_IS_CANCELED -> R.string.error_operation_is_canceled
        ErrorCode.FAILED_TO_LOAD_DATA -> R.string.error_failed_to_load_data
        ErrorCode.FAILED_TO_SAVE_CHANGES -> R.string.error_failed_to_save_changes
    }
}

/**
 * Retrieves string resource id of error message for passed [NetworkException]. Returns
 * R.string.error_unknown, if there is no corresponding localized error message.
 */
@StringRes
fun NetworkException.localizedErrorMessage() = when (reason) {
    UNKNOWN -> R.string.error_network_unknown_error
    UNKNOWN_HOST -> R.string.error_unknown_host
    NO_INTERNET -> R.string.error_no_internet
    TIMEOUT -> R.string.error_timeout
    HTTP -> R.string.error_http
    INVALID_RESPONSE -> R.string.error_invalid_response
    else -> R.string.error_network_unknown_error
}

/**
 * Converts Throwable to [ExpectedException] with specified [ErrorCode],
 * if throwable localized message is 'unknown error'.
 */
fun Throwable.toDetermined(errorCode: ErrorCode): Throwable {
    return if (localizedErrorMessage() != R.string.error_unknown) {
        this
    } else {
        ExpectedException(errorCode, this)
    }
}