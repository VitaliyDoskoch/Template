package com.doskoch.template.anime.di

import com.doskoch.template.anime.data.AnimeItem
import com.doskoch.template.anime.data.AnimeType
import com.doskoch.template.anime.data.PagedData
import com.doskoch.template.anime.navigation.AnimeFeatureNavigator
import com.doskoch.template.core.components.error.GlobalErrorHandler
import com.doskoch.template.core.components.paging.SimpleInMemoryStorage

interface AnimeFeature {
    val navigator: AnimeFeatureNavigator
    val globalErrorHandler: GlobalErrorHandler
    val repository: AnimeFeatureRepository
    val storage: SimpleInMemoryStorage<Int, AnimeItem>
}

interface AnimeFeatureRepository {

    suspend fun loadAnime(type: AnimeType, page: Int, pageSize: Int): PagedData

    suspend fun logout()

}