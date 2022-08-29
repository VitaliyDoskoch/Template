package com.doskoch.template.anime.screens.top.useCase

import com.doskoch.template.anime.data.AnimeType
import com.doskoch.template.anime.di.AnimeFeatureRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoadAnimeUseCase(
    private val repository: AnimeFeatureRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun invoke(type: AnimeType, key: Int, pageSize: Int) = withContext(dispatcher) {
        repository.loadAnime(type = type, page = key, pageSize = pageSize)
    }
}