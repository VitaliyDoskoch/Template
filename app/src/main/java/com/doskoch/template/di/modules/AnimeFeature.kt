package com.doskoch.template.di.modules

import androidx.navigation.navOptions
import com.doskoch.template.anime.data.AnimeItem
import com.doskoch.template.anime.di.AnimeFeature
import com.doskoch.template.anime.di.LogoutUseCase
import com.doskoch.template.anime.navigation.AnimeFeatureNavigator
import com.doskoch.template.api.jikan.JikanApiProvider
import com.doskoch.template.core.components.paging.SimpleInMemoryStorage
import com.doskoch.template.di.AppComponent
import com.doskoch.template.navigation.Node
import com.doskoch.template.repositories.anime.AnimeFeatureConverter
import com.doskoch.template.repositories.anime.AnimeFeatureRepositoryImpl
import com.doskoch.template.useCase.LogoutUseCaseImpl

fun animeFeatureModule(component: AppComponent) = object : AnimeFeature {
    override val navigator = object : AnimeFeatureNavigator() {
        override fun toSplash() = component.mainNavigator.toSplash(
            navOptions { popUpTo(Node.Anime.route) { inclusive = true } }
        )
    }

    override val globalErrorHandler = component.globalErrorHandler

    override val repository = AnimeFeatureRepositoryImpl(
        topService = JikanApiProvider.topService,
        converter = AnimeFeatureConverter()
    )

    override val storage = SimpleInMemoryStorage<Int, AnimeItem>()

    override val logoutUseCase = LogoutUseCase(LogoutUseCaseImpl(component.authorizationDataStore)::invoke)
}