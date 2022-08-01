package com.doskoch.template.api.the_movie_db.functions

import com.doskoch.template.api.the_movie_db.TheMovieDbApiModule
import com.doskoch.template.api.the_movie_db.common.error.ErrorResponse
import retrofit2.HttpException

fun HttpException.errorResponse() = response()?.errorBody()?.string()?.let {
    TheMovieDbApiModule.gson.fromJson(it, ErrorResponse::class.java)
}