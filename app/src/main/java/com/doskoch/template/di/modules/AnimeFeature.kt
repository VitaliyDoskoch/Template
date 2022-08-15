package com.doskoch.template.di.modules

import androidx.navigation.navOptions
import com.doskoch.template.anime.di.AnimeFeature
import com.doskoch.template.anime.di.AnimeFeatureGlobalNavigator
import com.doskoch.template.anime.navigation.AnimeNestedNavigator
import com.doskoch.template.anime.data.AnimeItem
import com.doskoch.template.api.jikan.JikanApiProvider
import com.doskoch.template.core.data.repository.AnimeRepository
import com.doskoch.template.core.components.paging.SimpleInMemoryStorage
import com.doskoch.template.di.AppComponent
import com.doskoch.template.navigation.Destinations
import com.doskoch.template.repositories.AnimeFeatureConverter
import com.doskoch.template.repositories.AnimeFeatureRepositoryImpl

fun animeFeatureModule(component: AppComponent) = object : AnimeFeature {
    override val globalNavigator = object : AnimeFeatureGlobalNavigator {
        override fun toSplash() = navOptions { popUpTo(Destinations.Anime.name) { inclusive = true } }
            .let(component.mainNavigator::toSplash)
    }

    override val nestedNavigator = AnimeNestedNavigator()

    override val globalErrorHandler = component.globalErrorHandler

    override val repository = AnimeFeatureRepositoryImpl(
        repository = AnimeRepository(JikanApiProvider.topService),
        authorizationDataStore = component.authorizationDataStore,
        converter = AnimeFeatureConverter()
    )

    override val storage = SimpleInMemoryStorage<Int, AnimeItem>()
}