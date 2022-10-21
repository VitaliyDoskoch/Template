package com.doskoch.template.api.jikan.services.anime.responses

import com.google.gson.annotations.SerializedName

data class GetAnimeResponse(
    @SerializedName("data")
    val data: Data
) {
    data class Data(
        @SerializedName("approved")
        val approved: Boolean,
        @SerializedName("duration")
        val duration: String,
        @SerializedName("episodes")
        val episodes: Int,
        @SerializedName("genres")
        val genres: List<Genre>,
        @SerializedName("images")
        val images: Images,
        @SerializedName("mal_id")
        val malId: Int,
        @SerializedName("popularity")
        val popularity: Int,
        @SerializedName("rank")
        val rank: Int,
        @SerializedName("rating")
        val rating: String,
        @SerializedName("score")
        val score: Float,
        @SerializedName("scored_by")
        val scoredBy: Int,
        @SerializedName("status")
        val status: String,
        @SerializedName("studios")
        val studios: List<Studio>,
        @SerializedName("synopsis")
        val synopsis: String,
        @SerializedName("themes")
        val themes: List<Theme>,
        @SerializedName("title")
        val title: String,
        @SerializedName("type")
        val type: String,
        @SerializedName("year")
        val year: Int
    ) {
        data class Genre(
            @SerializedName("mal_id")
            val malId: Int,
            @SerializedName("name")
            val name: String,
            @SerializedName("type")
            val type: String,
            @SerializedName("url")
            val url: String
        )

        data class Images(
            @SerializedName("webp")
            val webp: Webp
        ) {
            data class Webp(
                @SerializedName("image_url")
                val imageUrl: String,
                @SerializedName("large_image_url")
                val largeImageUrl: String,
                @SerializedName("small_image_url")
                val smallImageUrl: String
            )
        }

        data class Studio(
            @SerializedName("mal_id")
            val malId: Int,
            @SerializedName("name")
            val name: String,
            @SerializedName("type")
            val type: String,
            @SerializedName("url")
            val url: String
        )

        data class Theme(
            @SerializedName("mal_id")
            val malId: Int,
            @SerializedName("name")
            val name: String,
            @SerializedName("type")
            val type: String,
            @SerializedName("url")
            val url: String
        )
    }
}
