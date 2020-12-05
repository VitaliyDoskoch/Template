package com.doskoch.movies.features.films_all

import android.content.Context
import com.doskoch.apis.the_movie_db.services.discover.DiscoverService
import com.doskoch.movies.database.AppDatabase
import com.extensions.kotlin.components.di.ComponentInjector
import com.extensions.retrofit.components.service.ServiceConnector

interface AllFilmsFeature {
    val context: Context
    val discoverServiceConnector: ServiceConnector<DiscoverService>
    val database: AppDatabase
}

object AllFilmsFeatureInjector : ComponentInjector<AllFilmsFeature>()

internal val Injector
    get() = AllFilmsFeatureInjector.componentProvider!!.value