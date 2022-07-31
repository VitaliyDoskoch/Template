package com.doskoch.template.core.error

import android.annotation.SuppressLint
import android.content.Context
import com.doskoch.template.api.the_movie_db.common.error.ErrorResponse
import com.doskoch.template.core.R
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

    class OperationIsCanceled : CoreError() {
        override fun localizedMessage(context: Context) = context.getString(R.string.error_operation_is_canceled)
    }

    class NoInternet : CoreError() {
        override fun localizedMessage(context: Context) = context.getString(R.string.error_no_internet)
    }

    class Timeout : CoreError() {
        override fun localizedMessage(context: Context) = context.getString(R.string.error_timeout)
    }

    sealed class Remote(private val remoteMessage: String?) : CoreError() {

        companion object {
            fun create(exception: HttpException): CoreError = exception.extractMessage()
                ?.let { create(it.code, it.message) }
                ?: Unspecified(null)

            private fun create(code: ErrorResponse.Code?, message: String) = when (code) {
                ErrorResponse.Code.UNKNOWN -> Unspecified(message)
                else -> Unspecified(message)
            }
        }

        override fun localizedMessage(context: Context) = remoteMessage ?: context.getString(R.string.error_http)

        class Unspecified(remoteMessage: String?) : Remote(remoteMessage)
    }
}

fun Throwable.toCoreError(ifUnknown: CoreError) = toCoreError { ifUnknown }

fun Throwable.toCoreError(ifUnknown: (Throwable) -> CoreError = { CoreError.Unknown() }) = when (this) {
    is UnknownHostException -> CoreError.NoInternet()
    is SocketTimeoutException, is TimeoutException -> CoreError.Timeout()
    is InterruptedException -> CoreError.OperationIsCanceled()
    is AppIsOutdatedException -> CoreError.AppIsOutdated()
    is ShutdownException -> CoreError.Shutdown()
    is HttpException -> CoreError.Remote.create(this)
    else -> ifUnknown(this)
}