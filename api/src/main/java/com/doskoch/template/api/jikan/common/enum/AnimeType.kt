package com.doskoch.template.api.jikan.common.enum

import com.google.gson.annotations.SerializedName

enum class AnimeType {
    @SerializedName("tv")
    Tv,

    @SerializedName("movie")
    Movie,

    @SerializedName("ova")
    Ova,

    @SerializedName("special")
    Special,

    @SerializedName("ona")
    Ona,

    @SerializedName("music")
    Music
}