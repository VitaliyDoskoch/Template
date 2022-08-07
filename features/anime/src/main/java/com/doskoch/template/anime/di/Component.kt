package com.doskoch.template.anime.di

import com.doskoch.template.anime.AnimeNestedNavigator
import com.doskoch.template.anime.data.AnimeFilter
import com.doskoch.template.anime.data.AnimeItem
import com.doskoch.template.anime.data.AnimeType
import com.doskoch.template.anime.data.PagedData
import com.doskoch.template.core.error.GlobalErrorHandler
import com.doskoch.template.core.paging.SimpleInMemoryStorage

interface AnimeFeature {
    val navigator: AnimeFeatureNavigator
    val nestedNavigator: AnimeNestedNavigator
    val globalErrorHandler: GlobalErrorHandler
    val repository: AnimeFeatureRepository
    val storage: SimpleInMemoryStorage<Int, AnimeItem>
}

interface AnimeFeatureNavigator {

}

interface AnimeFeatureRepository {

    suspend fun loadAnime(type: AnimeType, filter: AnimeFilter, page: Int, pageSize: Int): PagedData

}