package com.doskoch.template.di.modules

import com.doskoch.template.anime.di.AnimeFeatureComponent
import com.doskoch.template.anime.navigation.AnimeFeatureNavigator
import com.doskoch.template.api.jikan.di.JikanApiModule
import com.doskoch.template.di.AppComponent

fun animeFeatureModule(component: AppComponent) = object : AnimeFeatureComponent {
    override val navigator = object : AnimeFeatureNavigator() {
        override fun toSplash() = component.navigator.toSplash()
    }

    override val globalErrorHandler = component.globalErrorHandler

    override val authorizationDataStore = component.authorizationDataStore

    override val topService = JikanApiModule.topService

    override val dbAnimeDao = component.appDatabase.dbAnimeDao()

    override val animeService = JikanApiModule.animeService
}
