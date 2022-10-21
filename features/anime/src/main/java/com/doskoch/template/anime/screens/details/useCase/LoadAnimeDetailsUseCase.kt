package com.doskoch.template.anime.screens.details.useCase

import com.doskoch.template.api.jikan.services.anime.AnimeService

class LoadAnimeDetailsUseCase(private val service: AnimeService) {
    suspend fun invoke(id: Int) = service.getAnime(id)
}