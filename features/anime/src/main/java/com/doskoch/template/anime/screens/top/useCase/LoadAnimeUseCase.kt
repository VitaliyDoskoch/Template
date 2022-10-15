package com.doskoch.template.anime.screens.top.useCase

import com.doskoch.template.api.jikan.common.enum.AnimeFilter
import com.doskoch.template.api.jikan.common.enum.AnimeType
import com.doskoch.template.api.jikan.services.TopService

class LoadAnimeUseCase(private val service: TopService) {
    suspend fun invoke(type: AnimeType, key: Int, pageSize: Int) = service.getTopAnime(type, AnimeFilter.Popularity, key, pageSize)
}