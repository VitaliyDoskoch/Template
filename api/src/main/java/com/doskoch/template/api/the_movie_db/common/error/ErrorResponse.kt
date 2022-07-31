package com.doskoch.template.api.the_movie_db.common.error

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("code")
    val code: Code
) {
    enum class Code {
        @SerializedName("UNKNOWN")
        UNKNOWN
    }
}