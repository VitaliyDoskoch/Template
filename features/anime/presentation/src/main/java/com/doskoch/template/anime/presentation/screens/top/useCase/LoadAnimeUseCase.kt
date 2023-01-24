package com.doskoch.template.anime.presentation.screens.top.useCase

import com.doskoch.template.api.jikan.common.enum.RemoteAnimeFilter
import com.doskoch.template.api.jikan.common.enum.RemoteAnimeType
import com.doskoch.template.api.jikan.services.top.TopService
import javax.inject.Inject

private val FILTER = RemoteAnimeFilter.Popularity

class LoadAnimeUseCase @Inject constructor(private val service: TopService) {
    suspend fun invoke(type: RemoteAnimeType, key: Int, pageSize: Int) = service.getTopAnime(type, FILTER, key, pageSize)
}
