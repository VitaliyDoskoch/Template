package com.doskoch.template.api.jikan.functions

import com.doskoch.template.api.jikan.JikanApiProvider
import com.doskoch.template.api.jikan.common.error.ErrorResponse
import retrofit2.HttpException

fun HttpException.errorResponse() = response()?.errorBody()?.string()?.let {
    JikanApiProvider.gson.fromJson(it, ErrorResponse::class.java)
}