package com.doskoch.api.the_movie_db.components.handlers

import com.doskoch.api.the_movie_db.HTTP
import com.doskoch.api.the_movie_db.INVALID_RESPONSE
import com.doskoch.api.the_movie_db.components.exceptions.NetworkException.Type
import com.doskoch.api.the_movie_db.components.validator.SelfValidator
import com.doskoch.api.the_movie_db.components.validator.validate
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
                is SelfValidator -> response.validate(body)
                is Collection<*> -> body.filterIsInstance<SelfValidator>().let { items ->
                    if (items.size == body.size) {
                        response.validate(items)
                    } else {
                        invalidTypeError(response)
                    }
                }
                else -> invalidTypeError(response)
            }
        } else {
            httpError(response)
        }
    }

    private fun R.validate(body: SelfValidator): Single<R> {
        return checkReport(this, body::class.java.simpleName, body.validate().printReport())
    }

    private fun R.validate(body: Collection<SelfValidator>): Single<R> {
        return checkReport(
            this,
            "Collection of ${body::class.java.simpleName} contains invalid items",
            body.validate().printReport()
        )
    }

    private fun checkReport(response: R, modelName: String, report: String): Single<R> {
        return if (report.isNotEmpty()) {
            invalidResponseError(response.url, "$modelName is not valid:\n$report")
        } else {
            Single.just(response)
        }
    }

    private fun invalidResponseError(url: String, message: String): Single<R> {
        return Single.error(
            com.doskoch.api.the_movie_db.components.exceptions.NetworkException(
                url,
                Type.SERVER,
                INVALID_RESPONSE,
                message
            )
        )
    }

    private fun invalidTypeError(response: R): Single<R> {
        return Single.error(
            com.doskoch.api.the_movie_db.components.exceptions.NetworkException(
                response.url,
                Type.UNKNOWN,
                "${this::class.java.simpleName} error",
                "Response.body (${response.body()?.let { it::class.java.simpleName }}) should be " +
                    "instance of ResponseBody, SelfValidator or Collection<SelfValidator>"
            )
        )
    }

    private fun httpError(response: R): Single<R> {
        return Single.error(
            com.doskoch.api.the_movie_db.components.exceptions.NetworkException(
                response.url,
                Type.HTTP,
                HTTP,
                response.message(),
                HttpException(response)
            )
        )
    }
}
