package com.doskoch.apis.the_movie_db

internal typealias Component = TheMovieDbComponent
internal typealias Injector = TheMovieDbInjector

interface TheMovieDbComponent {
    val isNetworkAvailable: () -> Boolean
}

object TheMovieDbInjector {
    lateinit var component: Component
}