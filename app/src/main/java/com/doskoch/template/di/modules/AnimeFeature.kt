package com.doskoch.template.di.modules

import androidx.navigation.navOptions
import com.doskoch.template.anime.di.AnimeFeatureComponent
import com.doskoch.template.anime.navigation.AnimeFeatureNavigator
import com.doskoch.template.api.jikan.JikanApiProvider
import com.doskoch.template.di.AppComponent
import com.doskoch.template.navigation.Node

fun animeFeatureModule(component: AppComponent) = object : AnimeFeatureComponent {
    override val navigator = object : AnimeFeatureNavigator() {
        override fun toSplash() = component.navigator.toSplash(
            navOptions { popUpTo(Node.Anime.route) { inclusive = true } }
        )
    }

    override val globalErrorHandler = component.globalErrorHandler

    override val authorizationDataStore = component.authorizationDataStore

    override val topService = JikanApiProvider.topService

    override val dbAnimeDao = component.appDatabase.dbAnimeDao()

    override val animeService = JikanApiProvider.animeService
}
