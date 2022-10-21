package com.doskoch.template.anime.di

import com.doskoch.template.anime.navigation.AnimeFeatureNavigator
import com.doskoch.template.api.jikan.services.anime.AnimeService
import com.doskoch.template.api.jikan.services.top.TopService
import com.doskoch.template.core.components.error.GlobalErrorHandler
import com.doskoch.template.core.store.AuthorizationDataStore
import com.doskoch.template.database.schema.anime.DbAnimeDao

interface AnimeFeatureComponent {
    val navigator: AnimeFeatureNavigator
    val globalErrorHandler: GlobalErrorHandler
    val authorizationDataStore: AuthorizationDataStore
    val topService: TopService
    val dbAnimeDao: DbAnimeDao
    val animeService: AnimeService
}
