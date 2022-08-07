package com.doskoch.template.api.jikan.services.responses

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
        @SerializedName("authors")
        val authors: List<Author>,
        @SerializedName("background")
        val background: String,
        @SerializedName("chapters")
        val chapters: Int,
        @SerializedName("demographics")
        val demographics: List<Demographic>,
        @SerializedName("explicit_genres")
        val explicitGenres: List<ExplicitGenre>,
        @SerializedName("favorites")
        val favorites: Int,
        @SerializedName("genres")
        val genres: List<Genre>,
        @SerializedName("images")
        val images: Images,
        @SerializedName("mal_id")
        val malId: Int,
        @SerializedName("members")
        val members: Int,
        @SerializedName("popularity")
        val popularity: Int,
        @SerializedName("published")
        val published: Published,
        @SerializedName("publishing")
        val publishing: Boolean,
        @SerializedName("rank")
        val rank: Int,
        @SerializedName("score")
        val score: Float,
        @SerializedName("scored_by")
        val scoredBy: Int,
        @SerializedName("serializations")
        val serializations: List<Serialization>,
        @SerializedName("status")
        val status: String,
        @SerializedName("synopsis")
        val synopsis: String,
        @SerializedName("themes")
        val themes: List<Theme>,
        @SerializedName("title")
        val title: String,
        @SerializedName("title_english")
        val titleEnglish: String,
        @SerializedName("title_japanese")
        val titleJapanese: String,
        @SerializedName("titles")
        val titles: List<String>,
        @SerializedName("type")
        val type: String,
        @SerializedName("url")
        val url: String,
        @SerializedName("volumes")
        val volumes: Int
    ) {
        data class Author(
            @SerializedName("mal_id")
            val malId: Int,
            @SerializedName("name")
            val name: String,
            @SerializedName("type")
            val type: String,
            @SerializedName("url")
            val url: String
        )

        data class Demographic(
            @SerializedName("mal_id")
            val malId: Int,
            @SerializedName("name")
            val name: String,
            @SerializedName("type")
            val type: String,
            @SerializedName("url")
            val url: String
        )

        data class ExplicitGenre(
            @SerializedName("mal_id")
            val malId: Int,
            @SerializedName("name")
            val name: String,
            @SerializedName("type")
            val type: String,
            @SerializedName("url")
            val url: String
        )

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
            @SerializedName("jpg")
            val jpg: Jpg,
            @SerializedName("webp")
            val webp: Webp
        ) {
            data class Jpg(
                @SerializedName("image_url")
                val imageUrl: String,
                @SerializedName("large_image_url")
                val largeImageUrl: String,
                @SerializedName("small_image_url")
                val smallImageUrl: String
            )

            data class Webp(
                @SerializedName("image_url")
                val imageUrl: String,
                @SerializedName("large_image_url")
                val largeImageUrl: String,
                @SerializedName("small_image_url")
                val smallImageUrl: String
            )
        }

        data class Published(
            @SerializedName("from")
            val from: String,
            @SerializedName("prop")
            val prop: Prop,
            @SerializedName("to")
            val to: String
        ) {
            data class Prop(
                @SerializedName("from")
                val from: From,
                @SerializedName("string")
                val string: String,
                @SerializedName("to")
                val to: To
            ) {
                data class From(
                    @SerializedName("day")
                    val day: Int,
                    @SerializedName("month")
                    val month: Int,
                    @SerializedName("year")
                    val year: Int
                )

                data class To(
                    @SerializedName("day")
                    val day: Int,
                    @SerializedName("month")
                    val month: Int,
                    @SerializedName("year")
                    val year: Int
                )
            }
        }

        data class Serialization(
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