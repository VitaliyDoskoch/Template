package com.doskoch.template.core.android.components.error

import retrofit2.HttpException

fun interface ErrorResponseParser {
    fun parse(exception: HttpException): CoreError
}
