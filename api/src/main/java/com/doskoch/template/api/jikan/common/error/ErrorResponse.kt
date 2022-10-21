package com.doskoch.template.api.jikan.common.error

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("type")
    val type: Type?,
    @SerializedName("message")
    val message: String
) {
    enum class Type {
        @SerializedName("RateLimitException")
        RateLimitException
    }
}
