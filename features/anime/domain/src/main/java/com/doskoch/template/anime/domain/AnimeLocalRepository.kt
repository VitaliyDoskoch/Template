package com.doskoch.template.anime.domain

import com.doskoch.template.anime.domain.model.AnimeItem
import kotlinx.coroutines.flow.Flow

interface AnimeLocalRepository {
    suspend fun deleteAnime(id: Int): Int
    fun isFavorite(id: Int): Flow<Boolean>
    fun animeIds(): Flow<List<Int>>
    suspend fun save(item: AnimeItem): Long
}
