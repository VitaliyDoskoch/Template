package com.doskoch.movies.features.films_all.repository.api

import com.doskoch.apis.the_movie_db.services.discover.DiscoverService
import com.doskoch.movies.features.films.functions.convertDate
import com.doskoch.movies.features.films_all.Injector
import com.extensions.kotlin.functions.beginningOfDay
import com.extensions.retrofit.components.service.ServiceConnector
import java.util.*

class AllFilmsApiRepositoryModule(
    val discoverServiceConnector: ServiceConnector<DiscoverService>
) {
    companion object {
        fun create(): AllFilmsApiRepositoryModule {
            return AllFilmsApiRepositoryModule(
                discoverServiceConnector = Injector.discoverServiceConnector
            )
        }
    }

    fun fromReleaseDate(): String {
        return Calendar.getInstance()
            .beginningOfDay()
            .apply { add(Calendar.WEEK_OF_YEAR, -2) }
            .let { convertDate(it.time.time) }
    }

    fun toReleaseDate(): String {
        return convertDate(Calendar.getInstance().time.time)
    }
}