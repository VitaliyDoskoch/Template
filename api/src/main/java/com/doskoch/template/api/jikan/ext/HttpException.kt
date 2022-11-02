package com.doskoch.template.api.jikan.ext

import com.doskoch.template.api.jikan.di.JikanApiModule
import com.doskoch.template.api.jikan.common.error.ErrorResponse
import retrofit2.HttpException

fun HttpException.errorResponse() = response()?.errorBody()?.string()?.let {
    try {
        JikanApiModule.gson.fromJson(it, ErrorResponse::class.java)
    } catch (t: Throwable) {
        null
    }
}
