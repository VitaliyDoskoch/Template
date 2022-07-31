package com.doskoch.template.api.the_movie_db.functions

import com.google.gson.Gson
import retrofit2.HttpException

fun HttpException.extractMessage(gson: Gson = ApiModule.provideGson()): ErrorResponse? {
    return response()?.errorBody()?.string()?.let {
        try {
            gson.fromJson(it, ErrorResponse::class.java)
        } catch (throwable: Throwable) {
            null
        }
    }
}