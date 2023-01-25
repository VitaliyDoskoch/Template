package com.doskoch.template.error

import com.doskoch.template.api.jikan.common.error.ErrorResponse
import com.doskoch.template.api.jikan.di.qualifiers.GsonForSerializing
import com.doskoch.template.core.android.components.error.CoreError
import com.doskoch.template.core.android.components.error.ErrorMapper
import com.google.gson.Gson
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorMapperImpl @Inject constructor(@GsonForSerializing private val gson: Gson) : ErrorMapper {

    override fun toCoreError(throwable: Throwable, ifUnknown: (Throwable) -> CoreError) = when (throwable) {
        is UnknownHostException -> CoreError.NoInternet
        is SocketTimeoutException, is TimeoutException -> CoreError.Timeout
        is InterruptedException, is CancellationException -> CoreError.OperationIsCanceled
        is HttpException -> map(throwable)
        else -> ifUnknown(throwable)
    }

    private fun map(exception: HttpException) = exception.response()?.errorBody()?.string()
        ?.let {
            try {
                gson.fromJson(it, ErrorResponse::class.java)
            } catch (t: Throwable) {
                null
            }
        }
        ?.let { response ->
            when (response.type) {
                ErrorResponse.Type.RateLimitException -> CoreError.Remote.RateLimit(response.status, response.message)
                else -> CoreError.Remote.Unspecified()
            }
        }
        ?: CoreError.Remote.Unspecified()
}
