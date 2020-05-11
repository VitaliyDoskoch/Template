package com.doskoch.apis.the_movie_db.components.handlers

import com.extensions.retrofit.components.exceptions.RetrofitException
import com.extensions.retrofit.functions.toNetworkException
import io.reactivex.Single
import io.reactivex.functions.Function
import retrofit2.Response

class CommonErrorHandler<R : Response<*>> : Function<Throwable, Single<R>> {

    override fun apply(throwable: Throwable): Single<R> {
        return Single.error(
            if (throwable is RetrofitException) throwable.toNetworkException() else throwable
        )
    }

}