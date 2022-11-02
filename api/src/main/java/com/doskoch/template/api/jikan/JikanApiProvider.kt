package com.doskoch.template.api.jikan

import com.doskoch.template.api.jikan.ext.addInterceptors
import com.doskoch.template.api.jikan.interceptors.ApiVersionInterceptor
import com.doskoch.template.api.jikan.services.anime.AnimeService
import com.doskoch.template.api.jikan.services.top.TopService
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.lang.reflect.Modifier
import java.util.concurrent.TimeUnit

@Suppress("MemberVisibilityCanBePrivate", "HasPlatformType")
object JikanApiProvider {

    val topService by lazy { retrofit.create(TopService::class.java) }

    val animeService by lazy { retrofit.create(AnimeService::class.java) }

    internal val retrofit by lazy {
        Retrofit.Builder().apply {
            baseUrl(if (BASE_URL.endsWith("/")) BASE_URL else "$BASE_URL/")
            client(okHttpClient)
            addConverterFactory(gsonConverterFactory)
        }
            .build()
    }

    internal val okHttpClient by lazy {
        OkHttpClient.Builder().apply {
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
            addInterceptors(
                listOf(
                    ApiVersionInterceptor(BASE_URL),
                    httpLoggingInterceptor
                )
            )
        }
            .build()
    }

    internal val httpLoggingInterceptor by lazy {
        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                val result: String = try {
                    GsonBuilder()
                        .setPrettyPrinting()
                        .create()
                        .toJson(JsonParser.parseString(message))
                } catch (t: Throwable) {
                    message
                }

                Timber.log(TIMBER_LOG_LEVEL, result)
            }
        })
            .apply { level = HTTP_LOG_LEVEL }
    }

    internal val gsonConverterFactory by lazy { GsonConverterFactory.create(gson) }

    internal val gson by lazy {
        GsonBuilder()
            .excludeFieldsWithModifiers(Modifier.TRANSIENT)
            .serializeNulls()
            .create()
    }
}
