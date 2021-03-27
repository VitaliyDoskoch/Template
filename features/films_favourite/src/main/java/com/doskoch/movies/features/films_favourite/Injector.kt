package com.doskoch.movies.features.films_favourite

import com.doskoch.movies.database.AppDatabase
import com.extensions.kotlin.components.di.ComponentInjector

interface FavouriteFilmsFeatureComponent {
    val database: AppDatabase
}

object FavouriteFilmsFeatureInjector : ComponentInjector<FavouriteFilmsFeatureComponent>()

internal val Injector
    get() = FavouriteFilmsFeatureInjector.componentProvider!!.value