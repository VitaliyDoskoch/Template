package com.doskoch.template.repositories

import com.doskoch.template.anime.data.AnimeFilter
import com.doskoch.template.anime.data.AnimeItem
import com.doskoch.template.anime.data.AnimeType
import com.doskoch.template.anime.data.PagedData
import com.doskoch.template.anime.di.AnimeFeatureRepository
import com.doskoch.template.api.jikan.services.responses.GetTopAnimeResponse
import com.doskoch.template.core.data.repository.AnimeRepository
import com.doskoch.template.api.jikan.common.enum.AnimeType as RemoteAnimeType
import com.doskoch.template.api.jikan.common.enum.AnimeFilter as RemoteAnimeFilter

class AnimeFeatureRepositoryImpl(
    private val repository: AnimeRepository,
    private val converter: AnimeFeatureConverter
) : AnimeFeatureRepository {

    override suspend fun loadAnime(type: AnimeType, filter: AnimeFilter, page: Int, pageSize: Int): PagedData {
        val response = repository.loadAnime(
            type = converter.toRemoteType(type),
            filter = converter.toRemoteFilter(filter),
            page = page,
            pageSize = pageSize
        )

        return PagedData(
            items = response.data.map(converter::toAnimeItem),
            lastPage = response.pagination.lastVisiblePage,
            hasNext = response.pagination.hasNextPage
        )
    }
}

class AnimeFeatureConverter {

    fun toRemoteType(type: AnimeType) = when(type) {
        AnimeType.Tv -> RemoteAnimeType.Tv
        AnimeType.Movie -> RemoteAnimeType.Movie
        AnimeType.Ova -> RemoteAnimeType.Ova
        AnimeType.Special -> RemoteAnimeType.Special
        AnimeType.Ona -> RemoteAnimeType.Ona
        AnimeType.Music -> RemoteAnimeType.Music
    }

    fun toRemoteFilter(filter: AnimeFilter) = when(filter) {
        AnimeFilter.Airing -> RemoteAnimeFilter.Airing
        AnimeFilter.Upcoming -> RemoteAnimeFilter.Upcoming
        AnimeFilter.Popularity -> RemoteAnimeFilter.Popularity
        AnimeFilter.Favorite -> RemoteAnimeFilter.Favorite
    }

    fun toAnimeItem(data: GetTopAnimeResponse.Data) = with(data) {
        AnimeItem(
            id = malId,
            approved = approved,
            imageUrl = images.webp.imageUrl,
            title = title,
            genres = genres.map { it.name },
            score = score,
            scoredBy = scoredBy,
            isFavorite = false
        )
    }
}