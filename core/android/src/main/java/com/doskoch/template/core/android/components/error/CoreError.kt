package com.doskoch.template.core.android.components.error

import android.content.Context
import com.doskoch.template.core.android.R
import com.doskoch.template.core.android.di.CoreAndroidInjector
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

abstract class CoreError {

    abstract fun localizedMessage(context: Context): String

    object Unknown : CoreError() {
        override fun localizedMessage(context: Context) = context.getString(R.string.error_unknown)
    }

    object FailedToLoadData : CoreError() {
        override fun localizedMessage(context: Context) = context.getString(R.string.error_failed_to_load_data)
    }

    object FailedToSaveChanges : CoreError() {
        override fun localizedMessage(context: Context) = context.getString(R.string.error_failed_to_save_changes)
    }

    object NoInternet : CoreError() {
        override fun localizedMessage(context: Context) = context.getString(R.string.error_no_internet)
    }

    object Timeout : CoreError() {
        override fun localizedMessage(context: Context) = context.getString(R.string.error_timeout)
    }

    object OperationIsCanceled : CoreError() {
        override fun localizedMessage(context: Context) = context.getString(R.string.error_operation_is_canceled)
    }

    abstract class Remote(val status: Int, val message: String?) : CoreError() {

        override fun localizedMessage(context: Context) = message ?: context.getString(R.string.error_http)

        class Unspecified : Remote(-1, null)

        class RateLimit(status: Int, message: String?) : Remote(status, message)
    }
}

fun Throwable.toCoreError(ifUnknown: CoreError = CoreError.Unknown) = toCoreError { ifUnknown }

fun Throwable.toCoreError(
    errorResponseParser: ErrorResponseParser = CoreAndroidInjector.errorResponseParser(),
    ifUnknown: (Throwable) -> CoreError
) = when (this) {
    is UnknownHostException -> CoreError.NoInternet
    is SocketTimeoutException, is TimeoutException -> CoreError.Timeout
    is InterruptedException, is CancellationException -> CoreError.OperationIsCanceled
    is HttpException -> errorResponseParser.parse(this)
    else -> ifUnknown(this)
}
