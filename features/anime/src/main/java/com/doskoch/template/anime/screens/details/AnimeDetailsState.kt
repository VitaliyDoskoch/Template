package com.doskoch.template.anime.screens.details

import com.doskoch.template.api.jikan.services.anime.responses.GetAnimeResponse
import com.doskoch.template.core.components.error.CoreError

data class AnimeDetailsState(
    val title: String,
    val isFavorite: Boolean,
    val screenState: ScreenState,
    val actions: Actions
) {
    sealed class ScreenState {
        object Loading : ScreenState()

        data class Content(
            val imageUrl: String,
            val approved: Boolean,
            val score: Float,
            val scoredBy: Int,
            val rank: Int,
            val popularity: Int,
            val members: Int,
            val favorites: Int,
            val genres: List<String>,
            val description: String,
            val type: String,
            val episodes: Int,
            val status: String,
            val premiered: String,
            val themes: List<String>,
            val duration: String,
            val rating: String,
            val studios: List<String>,
            val trailerUrl: String
        ) : ScreenState()

        data class Error(val error: CoreError) : ScreenState()
    }

    data class Actions(
        val onBackClick: () -> Unit,
        val onFavoriteClick: () -> Unit
    )
}

fun GetAnimeResponse.Data.toContent() = AnimeDetailsState.ScreenState.Content(
    imageUrl = images.webp.imageUrl,
    approved = approved,
    score = score,
    scoredBy = scoredBy,
    rank = rank,
    popularity = popularity,
    members = members,
    favorites = favorites,
    genres = genres.map { it.name },
    description = synopsis,
    type = type,
    episodes = episodes,
    status = status,
    premiered = year.toString(),
    themes = themes.map { it.name },
    duration = duration,
    rating = rating,
    studios = studios.map { it.name },
    trailerUrl = trailer.url
)
