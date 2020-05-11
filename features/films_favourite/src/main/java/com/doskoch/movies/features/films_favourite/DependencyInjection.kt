package com.doskoch.movies.features.films_favourite

import com.doskoch.movies.database.AppDatabase
import com.extensions.kotlin.components.di.ComponentInjector

internal typealias Component = FavouriteFilmsFeatureComponent
internal typealias Injector = FavouriteFilmsFeatureInjector

interface FavouriteFilmsFeatureComponent {
    val database: AppDatabase
}

object FavouriteFilmsFeatureInjector : ComponentInjector<Component>()