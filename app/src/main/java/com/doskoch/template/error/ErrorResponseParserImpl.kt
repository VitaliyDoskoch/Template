package com.doskoch.template.error

import com.doskoch.template.api.jikan.common.error.ErrorResponse
import com.doskoch.template.api.jikan.ext.errorResponse
import com.doskoch.template.core.android.components.error.CoreError
import com.doskoch.template.core.android.components.error.ErrorResponseParser
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorResponseParserImpl @Inject constructor() : ErrorResponseParser {
    override fun parse(exception: HttpException): CoreError {
        return exception.errorResponse()?.let { response ->
            when (response.type) {
                ErrorResponse.Type.RateLimitException -> CoreError.Remote.RateLimit(response.status, response.message)
                else -> CoreError.Remote.Unspecified()
            }
        } ?: CoreError.Remote.Unspecified()
    }
}