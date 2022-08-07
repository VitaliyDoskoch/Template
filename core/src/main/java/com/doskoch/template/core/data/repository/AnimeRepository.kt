package com.doskoch.template.core.data.repository

import com.doskoch.template.api.jikan.common.enum.AnimeFilter
import com.doskoch.template.api.jikan.common.enum.AnimeType
import com.doskoch.template.api.jikan.services.TopService

class AnimeRepository(
    private val service: TopService
) {

    suspend fun loadAnime(type: AnimeType, filter: AnimeFilter, page: Int, pageSize: Int) =
        service.getTopAnime(type, filter, page, pageSize)

}