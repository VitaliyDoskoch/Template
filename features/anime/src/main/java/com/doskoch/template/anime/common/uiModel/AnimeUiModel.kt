package com.doskoch.template.anime.common.uiModel

import com.doskoch.template.api.jikan.services.top.responses.GetTopAnimeResponse
import com.doskoch.template.database.schema.anime.DbAnime

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

fun GetTopAnimeResponse.Data.toUiModel(isFavorite: Boolean) = AnimeUiModel(
    id = malId,
    approved = approved,
    imageUrl = images.webp.imageUrl,
    title = title,
    genres = genres.map { it.name },
    score = score,
    scoredBy = scoredBy,
    isFavorite = isFavorite
)

fun DbAnime.toUiModel() = AnimeUiModel(
    id = id,
    approved = approved,
    imageUrl = imageUrl,
    title = title,
    genres = genres,
    score = score,
    scoredBy = scoredBy,
    isFavorite = true
)
