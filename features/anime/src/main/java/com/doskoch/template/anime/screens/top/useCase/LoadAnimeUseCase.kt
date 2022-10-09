package com.doskoch.template.anime.screens.top.useCase

import com.doskoch.template.anime.data.AnimeType
import com.doskoch.template.anime.di.AnimeFeatureRepository

class LoadAnimeUseCase(
    private val repository: AnimeFeatureRepository
) {
    suspend fun invoke(type: AnimeType, key: Int, pageSize: Int) = repository.loadAnime(type = type, page = key, pageSize = pageSize)
}