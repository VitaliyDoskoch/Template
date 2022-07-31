package com.doskoch.template.api.the_movie_db

import android.util.Log
import com.doskoch.template.api.BuildConfig
import com.doskoch.template.api.the_movie_db.components.interceptors.AuthorizationInterceptor
import com.doskoch.template.api.the_movie_db.functions.addInterceptors
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BASIC
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.lang.reflect.Modifier
import java.util.concurrent.TimeUnit

const val TIMBER_LOG_LEVEL = Log.DEBUG
val HTTP_LOG_LEVEL = if (BuildConfig.is_logging_enabled) BODY else BASIC

const val BASE_URL = "https://api.themoviedb.org/4/"

const val CONNECT_TIMEOUT = 45_000L
const val READ_TIMEOUT = 45_000L

object TheMovieDbApiConnector {

    private val httpLoggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
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

    private val gsonConverterFactory = GsonConverterFactory.create(
        GsonBuilder()
            .excludeFieldsWithModifiers(Modifier.TRANSIENT)
            .serializeNulls()
            .create()
    )

    fun createRetrofit() = Retrofit.Builder().apply {
        baseUrl(if (BASE_URL.endsWith("/")) BASE_URL else "$BASE_URL/")
        client(
            OkHttpClient.Builder().apply {
                connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                addInterceptors(
                    listOf(
                        AuthorizationInterceptor { BuildConfig.the_movie_db_api_key },
                        httpLoggingInterceptor
                    )
                )
            }
                .build()
        )
        addConverterFactory(gsonConverterFactory)
    }
        .build()
}
