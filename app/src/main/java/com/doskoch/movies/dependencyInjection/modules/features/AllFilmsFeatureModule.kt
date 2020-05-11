package com.doskoch.movies.dependencyInjection.modules.features

import android.content.Context
import com.doskoch.apis.the_movie_db.TheMovieDbApi
import com.doskoch.apis.the_movie_db.services.discover.DiscoverService
import com.doskoch.movies.dependencyInjection.AppComponent
import com.doskoch.movies.features.films_all.AllFilmsFeatureComponent
import com.extensions.retrofit.components.service.ServiceConnector

class AllFilmsFeatureModule(
    override val context: Context,
    override val discoverServiceConnector: ServiceConnector<DiscoverService>,
    override val database: com.doskoch.movies.database.AppDatabase
) : AllFilmsFeatureComponent {
    companion object {
        fun create(component: AppComponent): AllFilmsFeatureModule {
            return AllFilmsFeatureModule(
                context = component.application,
                discoverServiceConnector = TheMovieDbApi.discoverServiceConnector(),
                database = component.appDatabase
            )
        }
    }
}