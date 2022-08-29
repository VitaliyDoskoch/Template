package com.doskoch.template.api.jikan.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiVersion(val version: Int)

class ApiVersionInterceptor(private val baseUrl: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val version = request.tag(Invocation::class.java)?.method()?.getAnnotation(ApiVersion::class.java)?.version

        return chain.proceed(
            request.newBuilder()
                .apply {
                    version?.let { url("${baseUrl}v${version}/${request.url.toString().substringAfter(baseUrl)}") }
                }
                .build()
        )
    }
}