package com.doskoch.template.api.jikan.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val provideToken: () -> String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${provideToken()}")
                .build()
        )
    }
}
