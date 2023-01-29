package com.doskoch.template.anime.domain

import kotlinx.coroutines.flow.Flow

interface AnimeLocalRepository {
    suspend fun deleteAnime(id: Int): Int
    fun isFavorite(id: Int): Flow<Boolean>
}