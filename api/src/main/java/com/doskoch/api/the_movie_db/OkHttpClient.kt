package com.doskoch.api.the_movie_db

import okhttp3.Interceptor
import okhttp3.OkHttpClient

fun okHttpClient(
    configure: OkHttpClient.Builder.() -> OkHttpClient.Builder = { this }
): OkHttpClient {
    return configure(OkHttpClient.Builder()).build()
}

fun OkHttpClient.Builder.addInterceptors(interceptors: List<Interceptor>): OkHttpClient.Builder {
    return this.apply {
        interceptors.forEach {
            addInterceptor(it)
        }
    }
}