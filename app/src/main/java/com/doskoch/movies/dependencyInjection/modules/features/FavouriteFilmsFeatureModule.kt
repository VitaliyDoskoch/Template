package com.doskoch.movies.dependencyInjection.modules.features

import com.doskoch.movies.database.AppDatabase
import com.doskoch.movies.dependencyInjection.AppComponent
import com.doskoch.movies.features.films_favourite.FavouriteFilmsFeatureComponent

class FavouriteFilmsFeatureModule(
    override val database: AppDatabase
) : FavouriteFilmsFeatureComponent {
    companion object {
        fun create(component: AppComponent): FavouriteFilmsFeatureModule {
            return FavouriteFilmsFeatureModule(
                database = component.appDatabase
            )
        }
    }
}