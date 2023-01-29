package com.doskoch.template.anime.domain

interface AnimeLocalRepository {
    suspend fun deleteAnime(id: Int): Int
}