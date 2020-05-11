package com.extensions.retrofit.components.service

import com.extensions.retrofit.components.handlerProviders.ErrorHandlerProvider
import com.extensions.retrofit.components.handlerProviders.ResponseHandlerProvider
import io.reactivex.Single
import retrofit2.Response

/**
 * It is a helper class, which allows to inject custom
 * [ResponseHandlerProvider] and [ErrorHandlerProvider] to process response.
 */
class ServiceConnector<S : Any>(
    private val service: S,
    private val responseHandlerProvider: ResponseHandlerProvider? = null,
    private val errorHandlerProvider: ErrorHandlerProvider? = null
) {

    constructor(module: ServiceConnectorModule<S>) :
        this(module.service, module.responseHandlerProvider, module.errorHandlerProvider)

    fun <R : Response<*>> call(call: S.() -> Single<R>): Single<R> {
        return call(service)
            .let { single ->
                if (responseHandlerProvider != null) {
                    single.flatMap(responseHandlerProvider.provideHandler())
                } else {
                    single
                }
            }
            .let { single ->
                if (errorHandlerProvider != null) {
                    single.onErrorResumeNext(errorHandlerProvider.provideHandler())
                } else {
                    single
                }
            }
    }
}