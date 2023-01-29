package com.doskoch.template.anime.domain

import com.doskoch.template.anime.domain.model.AnimeDetails

interface AnimeRemoteRepository {
    suspend fun getAnime(id: Int): AnimeDetails
}