package com.doskoch.template.api.jikan.common.enum

import com.google.gson.annotations.SerializedName

enum class RemoteAnimeFilter {
    @SerializedName("airing")
    Airing,

    @SerializedName("upcoming")
    Upcoming,

    @SerializedName("bypopularity")
    Popularity,

    @SerializedName("favorite")
    Favorite
}
