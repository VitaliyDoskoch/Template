package com.doskoch.template.repositories

import com.doskoch.template.anime.data.AnimeItem
import com.doskoch.template.anime.data.AnimeType
import com.doskoch.template.anime.data.PagedData
import com.doskoch.template.anime.di.AnimeFeatureRepository
import com.doskoch.template.api.jikan.services.responses.GetTopAnimeResponse
import com.doskoch.template.core.data.repository.AnimeRepository
import com.doskoch.template.core.data.store.AuthorizationDataStore
import com.doskoch.template.api.jikan.common.enum.AnimeType as RemoteAnimeType

class AnimeFeatureRepositoryImpl(
    private val repository: AnimeRepository,
    private val authorizationDataStore: AuthorizationDataStore,
    private val converter: AnimeFeatureConverter
) : AnimeFeatureRepository {

    override suspend fun loadAnime(type: AnimeType, page: Int, pageSize: Int): PagedData {
        val response = repository.loadAnime(
            type = converter.toRemoteType(type),
            page = page,
            pageSize = pageSize
        )

        return PagedData(
            items = response.data.map(converter::toAnimeItem),
            lastPage = response.pagination.lastVisiblePage,
            hasNext = response.pagination.hasNextPage
        )
    }

    override suspend fun logout() {
        authorizationDataStore.updateAuthorized(false)
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