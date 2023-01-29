package com.doskoch.template.anime.domain.screens.top

import com.doskoch.template.anime.domain.AnimeRemoteRepository
import com.doskoch.template.anime.domain.model.AnimeType
import javax.inject.Inject

class LoadAnimeUseCase @Inject constructor(private val repository: AnimeRemoteRepository) {
    suspend fun invoke(type: AnimeType, key: Int, pageSize: Int) = repository.getTopAnime(type, key, pageSize)
}
