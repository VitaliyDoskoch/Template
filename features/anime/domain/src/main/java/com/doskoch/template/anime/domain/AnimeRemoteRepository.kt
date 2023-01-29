package com.doskoch.template.anime.domain

import com.doskoch.template.anime.domain.model.AnimeDetails
import com.doskoch.template.anime.domain.model.AnimeType
import com.doskoch.template.anime.domain.model.TopAnimePage

interface AnimeRemoteRepository {
    suspend fun getTopAnime(type: AnimeType, page: Int, limit: Int): TopAnimePage
    suspend fun getAnime(id: Int): AnimeDetails
}
