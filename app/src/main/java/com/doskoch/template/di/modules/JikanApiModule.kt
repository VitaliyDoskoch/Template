package com.doskoch.template.di.modules

import com.doskoch.template.BuildConfig
import com.doskoch.template.TIMBER_HTTP_LOG_LEVEL
import com.doskoch.template.api.jikan.di.qualifiers.GsonForLogging
import com.doskoch.template.api.jikan.di.qualifiers.GsonForSerializing
import com.doskoch.template.api.jikan.interceptors.ApiVersionInterceptor
import com.doskoch.template.api.jikan.services.anime.AnimeService
import com.doskoch.template.api.jikan.services.top.TopService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.lang.reflect.Modifier
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface JikanApiModule {
    companion object {
        val HTTP_LOG_LEVEL = if (BuildConfig.isLoggingEnabled) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        const val CONNECT_TIMEOUT = 15_000L
        const val READ_TIMEOUT = 15_000L

        const val BASE_URL = "https://api.jikan.moe/"

        @Provides
        @Singleton
        @GsonForLogging
        fun gsonForLogging() = GsonBuilder().setPrettyPrinting().create()

        @Provides
        @Singleton
        fun httpLoggingInterceptor(@GsonForLogging gson: Gson) = HttpLoggingInterceptor { message ->
            val result = try {
                gson.toJson(JsonParser.parseString(message))
            } catch (t: Throwable) {
                message
            }

            Timber.log(TIMBER_HTTP_LOG_LEVEL, result)
        }.apply { level = HTTP_LOG_LEVEL }

        @Provides
        @Singleton
        fun apiVersionInterceptor() = ApiVersionInterceptor(baseUrl = BASE_URL)

        @Provides
        @Singleton
        fun okHttpClient(loggingInterceptor: HttpLoggingInterceptor, apiVersionInterceptor: ApiVersionInterceptor) = OkHttpClient.Builder()
            .apply {
                connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                addInterceptor(loggingInterceptor)
                addInterceptor(apiVersionInterceptor)
            }
            .build()

        @Provides
        @Singleton
        @GsonForSerializing
        fun gsonForSerializing() = GsonBuilder()
            .excludeFieldsWithModifiers(Modifier.TRANSIENT)
            .serializeNulls()
            .create()

        @Provides
        @Singleton
        fun gsonConverterFactory(@GsonForSerializing gson: Gson) = GsonConverterFactory.create(gson)

        @Provides
        @Singleton
        fun retrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory) = Retrofit.Builder().apply {
            baseUrl(if (BASE_URL.endsWith("/")) BASE_URL else "$BASE_URL/")
            client(okHttpClient)
            addConverterFactory(gsonConverterFactory)
        }
            .build()

        @Provides
        @Singleton
        fun topService(retrofit: Retrofit) = retrofit.create(TopService::class.java)

        @Provides
        @Singleton
        fun animeService(retrofit: Retrofit) = retrofit.create(AnimeService::class.java)
    }
}
