package com.doskoch.apis.the_movie_db.services.discover.responses

import com.extensions.retrofit.components.validator.SelfValidator
import com.extensions.retrofit.components.validator.ValidationResult
import com.extensions.retrofit.components.validator.validate
import com.google.gson.annotations.SerializedName

data class GetMoviesResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
) : SelfValidator {

    data class Result(
        @SerializedName("adult")
        val adult: Boolean,
        @SerializedName("backdrop_path")
        val backdropPath: String?,
        @SerializedName("genre_ids")
        val genreIds: List<Int>,
        @SerializedName("id")
        val id: Long,
        @SerializedName("original_language")
        val originalLanguage: String,
        @SerializedName("original_title")
        val originalTitle: String,
        @SerializedName("overview")
        val overview: String,
        @SerializedName("popularity")
        val popularity: Double,
        @SerializedName("poster_path")
        val posterPath: String?,
        @SerializedName("release_date")
        val releaseDate: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("video")
        val video: Boolean,
        @SerializedName("vote_average")
        val voteAverage: Double,
        @SerializedName("vote_count")
        val voteCount: Int
    ) : SelfValidator {

        override fun validate(): ValidationResult {
            return ValidationResult(
                this::class to mutableSetOf<Pair<String, Any>>().apply {
                    if (id <= 0) add("id" to id)
                },
                null
            )
        }
    }

    override fun validate(): ValidationResult {
        return ValidationResult(
            this::class to mutableSetOf<Pair<String, Any>>().apply {
                if (page < 0) add("page" to page)
                if (totalPages < 0) add("totalPages" to totalPages)
                if (totalResults < 0) add("totalResults" to totalResults)
            },
            setOf(results.validate())
        )
    }
}