package com.extensions.retrofit.functions

import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit

fun Retrofit.Builder.addConverterFactories(factories: List<Converter.Factory>): Retrofit.Builder {
    return this.apply {
        factories.forEach {
            addConverterFactory(it)
        }
    }
}

fun Retrofit.Builder.addCallAdapterFactories(factories: List<CallAdapter.Factory>): Retrofit.Builder {
    return this.apply {
        factories.forEach {
            addCallAdapterFactory(it)
        }
    }
}