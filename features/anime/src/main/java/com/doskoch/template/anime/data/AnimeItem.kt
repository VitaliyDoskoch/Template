package com.doskoch.template.anime.data

data class AnimeItem(
    val id: Int,
    val approved: Boolean,
    val smallImageUrl: String,
    val title: String,
    val genres: List<String>,
    val score: Float,
    val scoredBy: Int,
    val isFavorite: Boolean
)