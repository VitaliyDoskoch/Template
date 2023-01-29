package com.doskoch.template.anime.domain.screens.details

import com.doskoch.template.anime.domain.AnimeRemoteRepository
import javax.inject.Inject

class LoadAnimeDetailsUseCase @Inject constructor(private val repository: AnimeRemoteRepository) {
    suspend fun invoke(id: Int) = repository.getAnime(id)
}
