package com.doskoch.api.the_movie_db.components.callAdapter

import com.doskoch.api.the_movie_db.components.exceptions.RetrofitException
import io.reactivex.Single
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Response

/**
 * It is a [CallAdapter] wrapper, that converts all possible errors in stream to [RetrofitException].
 */
class CallAdapterWrapper<R : Response<*>>(
    private val callAdapter: CallAdapter<R, Single<R>>
) : CallAdapter<R, Single<R>> by callAdapter {

    override fun adapt(call: Call<R>): Single<R> {
        return callAdapter.adapt(call)
            .onErrorResumeNext { throwable ->
                Single.error(RetrofitException(call, throwable))
            }
    }
}
