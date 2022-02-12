package com.doskoch.api.the_movie_db.components.service

import com.doskoch.api.the_movie_db.components.handlerProviders.CallHandlerProvider
import io.reactivex.Single
import retrofit2.Response

class ServiceConnector<S : Any>(
    private val service: S,
    private val callHandlerProvider: CallHandlerProvider? = null
) {

    fun <R : Response<*>> call(call: S.() -> Single<R>): Single<R> {
        return call(service).let { single ->
            callHandlerProvider?.provideHandler<R>()?.invoke(single) ?: single
        }
    }

}