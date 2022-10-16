package com.doskoch.template.anime.uiModel

import com.doskoch.template.api.jikan.services.responses.GetTopAnimeResponse

data class AnimeUiModel(
    val id: Int,
    val approved: Boolean,
    val imageUrl: String,
    val title: String,
    val genres: List<String>,
    val score: Float,
    val scoredBy: Int,
    val isFavorite: Boolean
)

fun GetTopAnimeResponse.Data.toUiModel() = AnimeUiModel(
    id = malId,
    approved = approved,
    imageUrl = images.webp.imageUrl,
    title = title,
    genres = genres.map { it.name },
    score = score,
    scoredBy = scoredBy,
    isFavorite = false
)