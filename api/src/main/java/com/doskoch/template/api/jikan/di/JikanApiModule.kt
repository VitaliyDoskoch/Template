package com.doskoch.template.api.jikan.di

import com.doskoch.template.api.jikan.BASE_URL
import com.doskoch.template.api.jikan.CONNECT_TIMEOUT
import com.doskoch.template.api.jikan.HTTP_LOG_LEVEL
import com.doskoch.template.api.jikan.READ_TIMEOUT
import com.doskoch.template.api.jikan.di.qualifiers.GsonForLogging
import com.doskoch.template.api.jikan.di.qualifiers.GsonForSerializing
import com.doskoch.template.api.jikan.interceptors.ApiVersionInterceptor
import com.doskoch.template.api.jikan.interceptors.HttpLoggerImpl
import com.doskoch.template.api.jikan.services.anime.AnimeService
import com.doskoch.template.api.jikan.services.top.TopService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Modifier
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface JikanApiModule {

    companion object {
        @Provides
        @Singleton
        @GsonForLogging
        fun gsonForLogging() = GsonBuilder().setPrettyPrinting().create()

        @Provides
        @Singleton
        fun httpLoggingInterceptor(httpLoggerImpl: HttpLoggerImpl) = HttpLoggingInterceptor(httpLoggerImpl).apply { level = HTTP_LOG_LEVEL }

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
