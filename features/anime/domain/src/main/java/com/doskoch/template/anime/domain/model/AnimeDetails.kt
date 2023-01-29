package com.doskoch.template.anime.domain.model

data class AnimeDetails(
    val imageUrl: String,
    val approved: Boolean,
    val score: Float,
    val scoredBy: Int,
    val rank: Int,
    val popularity: Int,
    val genres: List<String>,
    val description: String,
    val type: String,
    val episodes: Int,
    val status: String,
    val premiered: String,
    val themes: List<String>,
    val duration: String,
    val rating: String,
    val studios: List<String>
)
