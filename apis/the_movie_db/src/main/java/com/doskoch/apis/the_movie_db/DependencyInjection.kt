package com.doskoch.apis.the_movie_db

internal typealias Injector = TheMovieDbInjector

interface TheMovieDbApi {
    val isNetworkAvailable: () -> Boolean
}

object TheMovieDbInjector {
    lateinit var component: TheMovieDbApi
}