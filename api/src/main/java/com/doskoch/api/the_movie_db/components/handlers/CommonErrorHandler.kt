package com.doskoch.api.the_movie_db.components.handlers

import com.doskoch.api.the_movie_db.toNetworkException
import io.reactivex.Single
import io.reactivex.functions.Function
import retrofit2.Response

class CommonErrorHandler<R : Response<*>> : Function<Throwable, Single<R>> {

    override fun apply(throwable: Throwable): Single<R> {
        return Single.error(
            if (throwable is com.doskoch.api.the_movie_db.components.exceptions.RetrofitException) throwable.toNetworkException() else throwable
        )
    }

}