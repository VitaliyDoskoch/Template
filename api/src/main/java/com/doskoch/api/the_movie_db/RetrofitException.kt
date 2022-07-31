package com.doskoch.api.the_movie_db

import com.doskoch.api.the_movie_db.components.exceptions.NetworkException
import com.doskoch.api.the_movie_db.components.exceptions.NetworkException.Type
import com.doskoch.api.the_movie_db.components.exceptions.NoInternetException
import com.doskoch.api.the_movie_db.components.exceptions.RetrofitException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun RetrofitException.toNetworkException(): NetworkException {
    val url = call.request().url.toString()

    return when (val cause = cause) {
        is HttpException -> {
            NetworkException(
                url,
                Type.HTTP,
                HTTP,
                "${cause.code()}: ${cause.message()}",
                cause
            )
        }
        is IOException -> when (cause) {
            is NoInternetException -> {
                NetworkException(url, Type.NETWORK, NO_INTERNET, cause.message, cause)
            }
            is SocketTimeoutException -> {
                NetworkException(url, Type.NETWORK, TIMEOUT, cause.message, cause)
            }
            is UnknownHostException -> {
                NetworkException(url, Type.NETWORK, UNKNOWN_HOST, cause.message, cause)
            }
            else -> {
                NetworkException(url, Type.NETWORK, UNKNOWN, cause.message, cause)
            }
        }
        else -> NetworkException(url, Type.UNKNOWN, UNKNOWN, cause?.message, cause)
    }
}
