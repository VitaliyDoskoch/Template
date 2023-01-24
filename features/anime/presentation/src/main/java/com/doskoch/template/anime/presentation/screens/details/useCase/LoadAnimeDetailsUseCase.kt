package com.doskoch.template.anime.presentation.screens.details.useCase

import com.doskoch.template.api.jikan.services.anime.AnimeService
import javax.inject.Inject

class LoadAnimeDetailsUseCase @Inject constructor(private val service: AnimeService) {
    suspend fun invoke(id: Int) = service.getAnime(id)
}
