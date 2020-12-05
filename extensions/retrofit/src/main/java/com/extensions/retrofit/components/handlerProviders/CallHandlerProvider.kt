package com.extensions.retrofit.components.handlerProviders

import io.reactivex.Single
import retrofit2.Response

interface CallHandlerProvider {

    fun <R : Response<*>> provideHandler(): (Single<R>) -> Single<R>

}