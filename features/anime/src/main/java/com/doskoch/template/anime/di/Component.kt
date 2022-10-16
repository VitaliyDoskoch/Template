package com.doskoch.template.anime.di

import com.doskoch.template.anime.navigation.AnimeFeatureNavigator
import com.doskoch.template.api.jikan.services.TopService
import com.doskoch.template.api.jikan.services.responses.GetTopAnimeResponse
import com.doskoch.template.core.components.error.GlobalErrorHandler
import com.doskoch.template.core.components.paging.SimpleInMemoryStorage
import com.doskoch.template.core.store.AuthorizationDataStore
import com.doskoch.template.database.schema.anime.DbAnimeDao

interface AnimeFeature {
    val navigator: AnimeFeatureNavigator
    val globalErrorHandler: GlobalErrorHandler
    val authorizationDataStore: AuthorizationDataStore
    val topService: TopService
    val dbAnimeDao: DbAnimeDao
}