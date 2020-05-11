package com.extensions.retrofit.components.interceptors

import com.extensions.retrofit.components.exceptions.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Checks if network is available before request. If not, throws [NoInternetException] without
 * the request.
 */
class NetworkInterceptor(private val isNetworkAvailable: () -> Boolean) : Interceptor {

    @Throws(NoInternetException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!isNetworkAvailable()) {
            throw NoInternetException()
        } else {
            chain.proceed(chain.request().newBuilder().build())
        }
    }
}