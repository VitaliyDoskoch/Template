package com.doskoch.api.the_movie_db.components.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val provideToken: () -> String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${provideToken()}")
                .build()
        )
    }
}
