package com.doskoch.template.anime.data

import com.doskoch.template.anime.domain.AnimeRemoteRepository
import com.doskoch.template.anime.domain.model.AnimeType
import com.doskoch.template.anime.domain.model.TopAnimePage
import com.doskoch.template.api.jikan.common.enum.RemoteAnimeFilter
import com.doskoch.template.api.jikan.services.anime.AnimeService
import com.doskoch.template.api.jikan.services.top.TopService
import javax.inject.Inject

private val FILTER = RemoteAnimeFilter.Popularity

class AnimeRemoteRepositoryImpl @Inject constructor(
    private val topService: TopService,
    private val animeService: AnimeService,
    private val converter: AnimeDataConverter
) : AnimeRemoteRepository {

    override suspend fun getTopAnime(type: AnimeType, page: Int, limit: Int): TopAnimePage {
        val response = topService.getTopAnime(converter.toRemoteAnimeType(type), FILTER, page, limit)
        return TopAnimePage(response.data.map(converter::toAnimeItem), response.pagination.hasNextPage)
    }

    override suspend fun getAnime(id: Int) = animeService.getAnime(id).data.let(converter::toAnimeDetails)
}
