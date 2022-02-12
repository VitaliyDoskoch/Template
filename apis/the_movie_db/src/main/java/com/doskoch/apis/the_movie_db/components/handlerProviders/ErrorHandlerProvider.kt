package com.doskoch.apis.the_movie_db.components.handlerProviders

import io.reactivex.Single
import io.reactivex.functions.Function
import retrofit2.Response

interface ErrorHandlerProvider {

    fun <R : Response<*>> provideHandler(): Function<in Throwable, out Single<R>>

}