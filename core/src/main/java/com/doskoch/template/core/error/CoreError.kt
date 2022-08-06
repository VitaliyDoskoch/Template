package com.doskoch.template.core.error

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

    sealed class Remote(private val remoteMessage: String?) : CoreError() {

        override fun localizedMessage(context: Context) = remoteMessage ?: context.getString(R.string.error_http)

        class Unspecified(remoteMessage: String?) : Remote(remoteMessage)
    }
}

fun Throwable.toCoreError(ifUnknown: CoreError) = toCoreError { ifUnknown }

fun Throwable.toCoreError(ifUnknown: (Throwable) -> CoreError) = when (this) {
    is UnknownHostException -> CoreError.NoInternet()
    is SocketTimeoutException, is TimeoutException -> CoreError.Timeout()
    is InterruptedException -> CoreError.OperationIsCanceled()
    is HttpException -> errorResponse()
        ?.let { response ->
            when(response.code) {
                ErrorResponse.Code.UNKNOWN -> CoreError.Remote.Unspecified(message)
                else -> null
            }
        }
        ?: CoreError.Remote.Unspecified(null)
    else -> ifUnknown(this)
}