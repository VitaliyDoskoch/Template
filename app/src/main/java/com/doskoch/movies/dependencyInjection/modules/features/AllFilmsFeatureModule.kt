package com.doskoch.movies.dependencyInjection.modules.features

import android.content.Context
import com.doskoch.apis.the_movie_db.TheMovieDbApiProvider
import com.doskoch.apis.the_movie_db.services.discover.DiscoverService
import com.doskoch.movies.database.AppDatabase
import com.doskoch.movies.dependencyInjection.AppComponent
import com.doskoch.movies.features.films_all.AllFilmsFeature
import com.extensions.retrofit.components.service.ServiceConnector

class AllFilmsFeatureModule(
    override val context: Context,
    override val discoverServiceConnector: ServiceConnector<DiscoverService>,
    override val database: AppDatabase
) : AllFilmsFeature {
    companion object {
        fun create(component: AppComponent) = AllFilmsFeatureModule(
            context = component.application,
            discoverServiceConnector = TheMovieDbApiProvider.connector(),
            database = component.appDatabase
        )
    }
}