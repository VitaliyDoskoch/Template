package com.doskoch.template.anime.presentation.common.uiModel

import com.doskoch.template.anime.domain.model.AnimeItem

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

fun AnimeItem.toUiModel(isFavorite: Boolean) = AnimeUiModel(
    id = id,
    approved = approved,
    imageUrl = imageUrl,
    title = title,
    genres = genres,
    score = score,
    scoredBy = scoredBy,
    isFavorite = isFavorite
)
