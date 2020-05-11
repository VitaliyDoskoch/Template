package com.doskoch.apis.the_movie_db

import android.util.Log
import com.doskoch.apis.the_movie_db.components.handlers.CommonErrorHandler
import com.doskoch.apis.the_movie_db.components.handlers.CommonResponseHandler
import com.doskoch.apis.the_movie_db.components.interceptors.AuthorizationInterceptor
import com.doskoch.apis.the_movie_db.config.BASE_URL
import com.doskoch.apis.the_movie_db.config.CONNECT_TIMEOUT
import com.doskoch.apis.the_movie_db.config.READ_TIMEOUT
import com.doskoch.apis.the_movie_db.services.discover.DiscoverService
import com.extensions.retrofit.components.callAdapter.CallAdapterFactory
import com.extensions.retrofit.components.callAdapter.CallAdapterWrapper
import com.extensions.retrofit.components.handlerProviders.ErrorHandlerProvider
import com.extensions.retrofit.components.handlerProviders.ResponseHandlerProvider
import com.extensions.retrofit.components.interceptors.NetworkInterceptor
import com.extensions.retrofit.components.service.ServiceConnector
import com.extensions.retrofit.components.service.ServiceConnectorModule
import com.extensions.retrofit.functions.addInterceptors
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import io.reactivex.Single
import io.reactivex.functions.Function
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.lang.reflect.Modifier
import java.util.concurrent.TimeUnit

object TheMovieDbApi {

    fun discoverServiceConnector(
        module: ServiceConnectorModule<DiscoverService> = defaultServiceConnectorModule(
            DiscoverService::class.java
        )
    ): ServiceConnector<DiscoverService> {
        return ServiceConnector(module)
    }

    fun <S> defaultServiceConnectorModule(serviceClass: Class<S>): ServiceConnectorModule<S> {
        return ServiceConnectorModule(
            defaultRetrofit.create(serviceClass),
            object : ResponseHandlerProvider {
                override fun <R : Response<*>> provideHandler(): Function<R, Single<R>> {
                    return CommonResponseHandler()
                }
            },
            object : ErrorHandlerProvider {
                override fun <R : Response<*>> provideHandler(): Function<in Throwable, out Single<R>> {
                    return CommonErrorHandler()
                }
            }
        )
    }

    val defaultRetrofit: Retrofit
        get() {
            return Retrofit.Builder()
                .baseUrl(if (BASE_URL.endsWith("/")) BASE_URL else "$BASE_URL/")
                .client(defaultOkHttpClient)
                .addConverterFactory(defaultConverterFactory)
                .addCallAdapterFactory(defaultCallAdapterFactory)
                .build()
        }

    val defaultOkHttpClient: OkHttpClient
        get() {
            return OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptors(defaultInterceptors)
                .build()
        }

    val defaultInterceptors: List<Interceptor>
        get() {
            return listOf(
                NetworkInterceptor(TheMovieDbInjector.component.isNetworkAvailable),
                AuthorizationInterceptor { BuildConfig.the_movie_db_api_key },
                defaultHttpLoggingInterceptor
            )
        }

    val defaultHttpLoggingInterceptor: HttpLoggingInterceptor
        get() {
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
            }).apply {
                level = if (BuildConfig.is_logging_enabled) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.BASIC
                }
            }
        }

    val defaultConverterFactory: Converter.Factory
        get() {
            return GsonConverterFactory.create(
                GsonBuilder()
                    .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                    .serializeNulls()
                    .create()
            )
        }

    val defaultCallAdapterFactory: CallAdapterFactory<Response<*>, Single<Response<*>>>
        get() {
            return CallAdapterFactory { _, _, _, callAdapter ->
                CallAdapterWrapper(callAdapter)
            }
        }
}