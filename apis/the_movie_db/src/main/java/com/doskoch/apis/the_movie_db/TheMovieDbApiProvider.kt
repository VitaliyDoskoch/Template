package com.doskoch.apis.the_movie_db

import android.util.Log
import com.doskoch.apis.the_movie_db.components.interceptors.AuthorizationInterceptor
import com.doskoch.apis.the_movie_db.config.BASE_URL
import com.doskoch.apis.the_movie_db.config.CONNECT_TIMEOUT
import com.doskoch.apis.the_movie_db.config.READ_TIMEOUT
import com.doskoch.apis.the_movie_db.components.callAdapter.CallAdapterFactory
import com.doskoch.apis.the_movie_db.components.callAdapter.CallAdapterWrapper
import com.doskoch.apis.the_movie_db.components.handlerProviders.CallHandlerProvider
import com.doskoch.apis.the_movie_db.components.interceptors.NetworkInterceptor
import com.doskoch.apis.the_movie_db.components.service.ServiceConnector
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.doskoch.apis.the_movie_db.components.handlers.CallHandler
import com.doskoch.apis.the_movie_db.components.handlers.CommonErrorHandler
import com.doskoch.apis.the_movie_db.components.handlers.CommonResponseHandler
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BASIC
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.lang.reflect.Modifier
import java.util.concurrent.TimeUnit

object TheMovieDbApiProvider {

    inline fun <reified S : Any> connector(
        retrofit: Retrofit = defaultRetrofit(),
        callHandlerProvider: CallHandlerProvider = callHandlerProvider()
    ): ServiceConnector<S> {
        return ServiceConnector(retrofit.create(S::class.java), callHandlerProvider)
    }

    fun defaultRetrofit(): Retrofit {
        return retrofit {
            baseUrl(if (BASE_URL.endsWith("/")) BASE_URL else "$BASE_URL/")
            client(
                okHttpClient {
                    connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                    readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                    addInterceptors(defaultInterceptors())
                }
            )
            addConverterFactory(defaultGsonConverterFactory())
            addCallAdapterFactory(defaultCallAdapterFactory())
        }
    }

    fun defaultInterceptors(): List<Interceptor> {
        return listOf(
            NetworkInterceptor(Injector.isNetworkAvailable),
            AuthorizationInterceptor { BuildConfig.the_movie_db_api_key },
            defaultLoggingInterceptor()
        )
    }

    fun defaultLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                val result: String = try {
                    GsonBuilder()
                        .setPrettyPrinting()
                        .create()
                        .toJson(JsonParser.parseString(message))
                } catch (t: Throwable) {
                    message
                }

                Timber.log(Log.DEBUG, result)
            }
        })
            .apply { level = if (BuildConfig.is_logging_enabled) BODY else BASIC }
    }

    fun defaultGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(
            GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                .serializeNulls()
                .create()
        )
    }

    fun defaultCallAdapterFactory(): CallAdapterFactory<Response<*>, Single<Response<*>>> {
        return CallAdapterFactory { _, _, _, callAdapter -> CallAdapterWrapper(callAdapter) }
    }

    fun callHandlerProvider(): CallHandlerProvider {
        return object : CallHandlerProvider {
            override fun <R : Response<*>> provideHandler(): (Single<R>) -> Single<R> {
                return CallHandler(
                    CommonResponseHandler(),
                    CommonErrorHandler()
                )
            }
        }
    }
}