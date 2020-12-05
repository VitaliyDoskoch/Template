package com.doskoch.movies.dependencyInjection.modules.features

import com.doskoch.movies.database.AppDatabase
import com.doskoch.movies.dependencyInjection.AppComponent
import com.doskoch.movies.features.films_favourite.FavouriteFilmsFeature

class FavouriteFilmsFeatureModule(
    override val database: AppDatabase
) : FavouriteFilmsFeature {
    companion object {
        fun create(component: AppComponent) = FavouriteFilmsFeatureModule(
            database = component.appDatabase
        )
    }
}