package com.doskoch.api.the_movie_db.components.interceptors

import com.doskoch.api.the_movie_db.components.exceptions.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Checks if network is available before request. If not, throws [NoInternetException] without
 * the request.
 */
class NetworkInterceptor(private val isNetworkAvailable: () -> Boolean) : Interceptor {

    @Throws(com.doskoch.api.the_movie_db.components.exceptions.NoInternetException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!isNetworkAvailable()) {
            throw com.doskoch.api.the_movie_db.components.exceptions.NoInternetException()
        } else {
            chain.proceed(chain.request().newBuilder().build())
        }
    }
}
