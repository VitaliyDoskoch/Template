package com.doskoch.template.anime.domain.model

data class TopAnimePage(
    val items: List<AnimeItem>,
    val hasNextPage: Boolean
)

data class AnimeItem(
    val id: Int,
    val approved: Boolean,
    val imageUrl: String,
    val title: String,
    val genres: List<String>,
    val score: Float,
    val scoredBy: Int
)
