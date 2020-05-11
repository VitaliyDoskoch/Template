package com.doskoch.apis.the_movie_db.components.handlers

import com.extensions.retrofit.components.HTTP
import com.extensions.retrofit.components.INVALID_RESPONSE
import com.extensions.retrofit.components.exceptions.NetworkException
import com.extensions.retrofit.components.exceptions.NetworkException.Type
import com.extensions.retrofit.components.validator.SelfValidator
import io.reactivex.Single
import io.reactivex.functions.Function
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

class CommonResponseHandler<R : Response<*>> : Function<R, Single<R>> {

    private val R.url: String
        get() = raw().request.url.toString()

    override fun apply(response: R): Single<R> {
        return if (response.isSuccessful) {
            when (val body = response.body()) {
                is ResponseBody -> Single.just(response)
                is SelfValidator -> validateResponse(response, body)
                else -> invalidTypeError(response)
            }
        } else {
            httpError(response)
        }
    }

    private fun validateResponse(response: R, body: SelfValidator): Single<R> {
        return body.validate().printReport().let { report ->
            if (report.isNotEmpty()) {
                Single.error(
                    NetworkException(
                        response.url,
                        Type.SERVER,
                        INVALID_RESPONSE,
                        "${body::class.java.simpleName} is not valid:\n$report"
                    )
                )
            } else {
                Single.just(response)
            }
        }
    }

    private fun invalidTypeError(response: R): Single<R> {
        return Single.error(
            NetworkException(
                response.url,
                Type.UNKNOWN,
                "${this::class.java.simpleName} error",
                "Response.body should be instance of ResponseBody or SelfValidator"
            )
        )
    }

    private fun httpError(response: R): Single<R> {
        return Single.error(
            NetworkException(
                response.url,
                Type.HTTP,
                HTTP,
                response.errorBody()?.string(),
                HttpException(response)
            )
        )
    }
}