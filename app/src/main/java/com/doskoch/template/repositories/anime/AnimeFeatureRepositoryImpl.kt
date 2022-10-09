package com.doskoch.template.repositories.anime

import com.doskoch.template.anime.data.AnimeType
import com.doskoch.template.anime.data.PagedData
import com.doskoch.template.anime.di.AnimeFeatureRepository
import com.doskoch.template.api.jikan.common.enum.AnimeFilter
import com.doskoch.template.api.jikan.services.TopService
import com.doskoch.template.data.store.AuthorizationDataStore

class AnimeFeatureRepositoryImpl(
    private val topService: TopService,
    private val converter: AnimeFeatureConverter
) : AnimeFeatureRepository {

    override suspend fun loadAnime(type: AnimeType, page: Int, pageSize: Int): PagedData {
        val response = topService.getTopAnime(converter.toRemoteType(type), AnimeFilter.Popularity, page, pageSize)

        return PagedData(
            items = response.data.map(converter::toAnimeItem),
            lastPage = response.pagination.lastVisiblePage,
            hasNext = response.pagination.hasNextPage
        )
    }
}