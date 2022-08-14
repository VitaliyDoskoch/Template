package com.doskoch.template.anime.di

import com.doskoch.template.anime.navigation.AnimeNestedNavigator
import com.doskoch.template.anime.data.AnimeFilter
import com.doskoch.template.anime.data.AnimeItem
import com.doskoch.template.anime.data.AnimeType
import com.doskoch.template.anime.data.PagedData
import com.doskoch.template.core.components.error.GlobalErrorHandler
import com.doskoch.template.core.components.paging.SimpleInMemoryStorage

interface AnimeFeature {
    val globalNavigator: AnimeFeatureGlobalNavigator
    val nestedNavigator: AnimeNestedNavigator
    val globalErrorHandler: GlobalErrorHandler
    val repository: AnimeFeatureRepository
    val storage: SimpleInMemoryStorage<Int, AnimeItem>
}

interface AnimeFeatureGlobalNavigator {

}

interface AnimeFeatureRepository {

    suspend fun loadAnime(type: AnimeType, filter: AnimeFilter, page: Int, pageSize: Int): PagedData

}