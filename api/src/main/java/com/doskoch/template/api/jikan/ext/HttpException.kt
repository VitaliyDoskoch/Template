package com.doskoch.template.api.jikan.ext

import com.doskoch.template.api.jikan.common.error.ErrorResponse
import com.doskoch.template.api.jikan.di.JikanApiModule
import retrofit2.HttpException

private val gson by lazy { JikanApiModule.gsonForSerializing() }

fun HttpException.errorResponse() = response()?.errorBody()?.string()?.let {
    try {
        gson.fromJson(it, ErrorResponse::class.java)
    } catch (t: Throwable) {
        null
    }
}
