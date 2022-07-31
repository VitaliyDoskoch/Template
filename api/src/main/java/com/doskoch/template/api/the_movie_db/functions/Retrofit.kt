package com.doskoch.template.api.the_movie_db.functions

import retrofit2.Converter
import retrofit2.Retrofit

fun Retrofit.Builder.addConverterFactories(factories: List<Converter.Factory>) = apply {
    factories.forEach(::addConverterFactory)
}
