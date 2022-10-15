package com.doskoch.template.di.modules

import androidx.navigation.navOptions
import com.doskoch.template.anime.data.AnimeItem
import com.doskoch.template.anime.di.AnimeFeature
import com.doskoch.template.anime.navigation.AnimeFeatureNavigator
import com.doskoch.template.api.jikan.JikanApiProvider
import com.doskoch.template.api.jikan.services.TopService
import com.doskoch.template.api.jikan.services.responses.GetTopAnimeResponse
import com.doskoch.template.core.components.paging.SimpleInMemoryStorage
import com.doskoch.template.di.AppComponent
import com.doskoch.template.navigation.Node

fun animeFeatureModule(component: AppComponent) = object : AnimeFeature {
    override val navigator = object : AnimeFeatureNavigator() {
        override fun toSplash() = component.mainNavigator.toSplash(
            navOptions { popUpTo(Node.Anime.route) { inclusive = true } }
        )
    }

    override val globalErrorHandler = component.globalErrorHandler

    override val authorizationDataStore = component.authorizationDataStore

    override val topService = JikanApiProvider.topService

    override val storage = SimpleInMemoryStorage<Int, GetTopAnimeResponse.Data>()

    override val dbAnimeDao = component.appDatabase.dbAnimeDao()
}