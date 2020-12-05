package com.doskoch.movies.dependencyInjection.modules.apis

import com.doskoch.apis.the_movie_db.TheMovieDbApi
import com.doskoch.movies.dependencyInjection.AppComponent
import com.extensions.android.functions.isNetworkAvailable

class TheMovieDbApiModule(
    override val isNetworkAvailable: () -> Boolean
) : TheMovieDbApi {
    companion object {
        fun create(component: AppComponent): TheMovieDbApiModule {
            return TheMovieDbApiModule(
                isNetworkAvailable = { component.application.isNetworkAvailable() }
            )
        }
    }
}