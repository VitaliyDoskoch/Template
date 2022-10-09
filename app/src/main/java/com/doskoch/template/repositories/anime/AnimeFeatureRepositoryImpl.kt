package com.doskoch.template.repositories.anime

import com.doskoch.template.anime.data.AnimeType
import com.doskoch.template.anime.data.PagedData
import com.doskoch.template.anime.di.AnimeFeatureRepository
import com.doskoch.template.core.data.repository.AnimeRepository
import com.doskoch.template.core.data.store.AuthorizationDataStore

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