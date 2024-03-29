package com.doskoch.template.anime.screens.top.useCase

import com.doskoch.template.api.jikan.common.enum.RemoteAnimeFilter
import com.doskoch.template.api.jikan.common.enum.RemoteAnimeType
import com.doskoch.template.api.jikan.services.top.TopService

private val FILTER = RemoteAnimeFilter.Popularity

class LoadAnimeUseCase(private val service: TopService) {
    suspend fun invoke(type: RemoteAnimeType, key: Int, pageSize: Int) = service.getTopAnime(type, FILTER, key, pageSize)
}
