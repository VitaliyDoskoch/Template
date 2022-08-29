package com.doskoch.template.core.components.error

import android.content.Context
import com.doskoch.template.api.jikan.common.error.ErrorResponse
import com.doskoch.template.api.jikan.functions.errorResponse
import com.doskoch.template.core.R
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

sealed class CoreError {

    abstract fun localizedMessage(context: Context): String

    class Unknown : CoreError() {
        override fun localizedMessage(context: Context) = context.getString(R.string.error_unknown)
    }

    class FailedToLoadData : CoreError() {
        override fun localizedMessage(context: Context) = context.getString(R.string.error_failed_to_load_data)
    }

    class FailedToSaveChanges : CoreError() {
        override fun localizedMessage(context: Context) = context.getString(R.string.error_failed_to_save_changes)
    }

    class NoInternet : CoreError() {
        override fun localizedMessage(context: Context) = context.getString(R.string.error_no_internet)
    }

    class Timeout : CoreError() {
        override fun localizedMessage(context: Context) = context.getString(R.string.error_timeout)
    }

    class OperationIsCanceled : CoreError() {
        override fun localizedMessage(context: Context) = context.getString(R.string.error_operation_is_canceled)
    }

    class InvalidEmail : CoreError() {
        override fun localizedMessage(context: Context) = context.getString(R.string.error_invalid_email)
    }

    sealed class Remote(val status: Int, val message: String?) : CoreError() {

        override fun localizedMessage(context: Context) = message ?: context.getString(R.string.error_http)

        class Unspecified : Remote(-1, null)

        class RateLimit(status: Int, message: String?) : Remote(status, message)
    }
}

fun Throwable.toCoreError(ifUnknown: CoreError = CoreError.Unknown()) = toCoreError { ifUnknown }

fun Throwable.toCoreError(ifUnknown: (Throwable) -> CoreError) = when (this) {
    is UnknownHostException -> CoreError.NoInternet()
    is SocketTimeoutException, is TimeoutException -> CoreError.Timeout()
    is InterruptedException -> CoreError.OperationIsCanceled()
    is HttpException -> errorResponse()
        ?.let { response ->
            when(response.type) {
                ErrorResponse.Type.RateLimitException -> CoreError.Remote.RateLimit(response.status, response.message)
                else -> CoreError.Remote.Unspecified()
            }
        }
        ?: CoreError.Remote.Unspecified()
    else -> ifUnknown(this)
}