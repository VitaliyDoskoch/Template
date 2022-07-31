package com.doskoch.template.api.the_movie_db.functions

import okhttp3.Interceptor
import okhttp3.OkHttpClient

fun OkHttpClient.Builder.addInterceptors(interceptors: List<Interceptor>) = apply {
    interceptors.forEach(::addInterceptor)
}
