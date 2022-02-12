package com.doskoch.api.the_movie_db.components.handlers

import io.reactivex.Single
import io.reactivex.functions.Function
import retrofit2.Response

class CallHandler<R : Response<*>>(
    private val responseHandlerProvider: Function<R, Single<R>>,
    private val errorHandlerProvider: Function<in Throwable, out Single<R>>
) : (Single<R>) -> Single<R> {

    override fun invoke(call: Single<R>): Single<R> {
        return call
            .flatMap(responseHandlerProvider)
            .onErrorResumeNext(errorHandlerProvider)
    }
}