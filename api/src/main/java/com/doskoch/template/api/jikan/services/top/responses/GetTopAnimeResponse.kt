package com.doskoch.template.api.jikan.services.top.responses

import com.google.gson.annotations.SerializedName

data class GetTopAnimeResponse(
    @SerializedName("data")
    val data: List<Data>,
    @SerializedName("pagination")
    val pagination: Pagination
) {
    data class Data(
        @SerializedName("approved")
        val approved: Boolean,
        @SerializedName("genres")
        val genres: List<Genre>,
        @SerializedName("images")
        val images: Images,
        @SerializedName("mal_id")
        val malId: Int,
        @SerializedName("rank")
        val rank: Int,
        @SerializedName("score")
        val score: Float,
        @SerializedName("scored_by")
        val scoredBy: Int,
        @SerializedName("status")
        val status: String,
        @SerializedName("title")
        val title: String,
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
    }

    data class Pagination(
        @SerializedName("has_next_page")
        val hasNextPage: Boolean,
        @SerializedName("items")
        val items: Items,
        @SerializedName("last_visible_page")
        val lastVisiblePage: Int
    ) {
        data class Items(
            @SerializedName("count")
            val count: Int,
            @SerializedName("per_page")
            val perPage: Int,
            @SerializedName("total")
            val total: Int
        )
    }
}
