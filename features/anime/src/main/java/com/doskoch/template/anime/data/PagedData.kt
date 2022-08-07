package com.doskoch.template.anime.data

data class PagedData(
    val items: List<AnimeItem>,
    val lastPage: Int,
    val hasNext: Boolean
)