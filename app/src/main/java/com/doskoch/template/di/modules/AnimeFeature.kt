package com.doskoch.template.di.modules

import com.doskoch.template.anime.di.AnimeFeature
import com.doskoch.template.anime.di.AnimeFeatureNavigator
import com.doskoch.template.anime.AnimeNestedNavigator
import com.doskoch.template.anime.data.AnimeItem
import com.doskoch.template.anime.di.AnimeFeatureRepository
import com.doskoch.template.api.jikan.JikanApiProvider
import com.doskoch.template.core.data.repository.AnimeRepository
import com.doskoch.template.core.paging.SimpleInMemoryStorage
import com.doskoch.template.di.AppComponent
import com.doskoch.template.repositories.AnimeFeatureConverter
import com.doskoch.template.repositories.AnimeFeatureRepositoryImpl

fun animeFeatureModule(component: AppComponent) = object : AnimeFeature {
    override val navigator = object : AnimeFeatureNavigator {

    }

    override val nestedNavigator = AnimeNestedNavigator()

    override val globalErrorHandler = component.globalErrorHandlerHolder.handler

    override val repository = AnimeFeatureRepositoryImpl(
        repository = AnimeRepository(JikanApiProvider.topService),
        converter = AnimeFeatureConverter()
    )

    override val storage = SimpleInMemoryStorage<Int, AnimeItem>()
}