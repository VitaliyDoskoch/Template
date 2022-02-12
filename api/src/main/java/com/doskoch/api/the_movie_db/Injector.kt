package com.doskoch.api.the_movie_db

interface TheMovieDbApi {
    val isNetworkAvailable: () -> Boolean
}

object TheMovieDbApiInjector {
    lateinit var component: TheMovieDbApi
}

internal val Injector: TheMovieDbApi
    get() = TheMovieDbApiInjector.component