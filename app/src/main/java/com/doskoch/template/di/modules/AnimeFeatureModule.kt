package com.doskoch.template.di.modules

import com.doskoch.template.anime.di.AnimeFeatureComponent
import com.doskoch.template.anime.navigation.AnimeFeatureNavigator
import com.doskoch.template.api.jikan.di.JikanApiModule
import com.doskoch.template.di.AppComponent
import com.doskoch.template.navigation.MainNavigator

fun animeFeatureModule(component: AppComponent, mainNavigator: MainNavigator) = object : AnimeFeatureComponent {
    override val navigator = object : AnimeFeatureNavigator() {
        override fun toSplash() = mainNavigator.toSplashFromAnime()
    }

    override val globalErrorHandler = component.globalErrorHandler

    override val topService = JikanApiModule.topService

    override val dbAnimeDao = component.appDatabase.dbAnimeDao()

    override val animeService = JikanApiModule.animeService
}
