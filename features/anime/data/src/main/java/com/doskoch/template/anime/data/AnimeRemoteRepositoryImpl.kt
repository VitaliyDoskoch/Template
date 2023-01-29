package com.doskoch.template.anime.data

import com.doskoch.template.anime.domain.AnimeRemoteRepository
import com.doskoch.template.api.jikan.services.anime.AnimeService
import com.doskoch.template.api.jikan.services.anime.responses.GetAnimeResponse
import javax.inject.Inject

class AnimeRemoteRepositoryImpl @Inject constructor(
    private val service: AnimeService,
    private val converter: AnimeDataConverter
) : AnimeRemoteRepository {
    override suspend fun getAnime(id: Int) = service.getAnime(id).data.let(converter::toAnimeDetails)
}