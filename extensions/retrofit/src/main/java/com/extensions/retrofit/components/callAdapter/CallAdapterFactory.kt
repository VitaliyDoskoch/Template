package com.extensions.retrofit.components.callAdapter

import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.reflect.Type

/**
 * It is a [CallAdapter.Factory], that allows to inject custom wrapper of [CallAdapter].
 */
class CallAdapterFactory<R, T>(
    private val provideWrapper: (returnType: Type,
                                 annotations: Array<out Annotation>,
                                 retrofit: Retrofit,
                                 callAdapter: CallAdapter<R, T>) -> CallAdapter<R, T>
) : CallAdapter.Factory() {

    private val factory = RxJava2CallAdapterFactory.create()

    @Suppress("UNCHECKED_CAST")
    override fun get(returnType: Type,
                     annotations: Array<out Annotation>,
                     retrofit: Retrofit): CallAdapter<R, T>? {
        return (factory.get(returnType, annotations, retrofit) as? CallAdapter<R, T>)
            ?.let { adapter ->
                provideWrapper(returnType, annotations, retrofit, adapter)
            }
    }
}