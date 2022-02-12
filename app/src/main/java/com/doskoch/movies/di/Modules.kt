package com.doskoch.movies.di

import android.app.Application
import android.content.Context
import com.doskoch.apis.the_movie_db.TheMovieDbApi
import com.doskoch.apis.the_movie_db.TheMovieDbApiProvider
import com.doskoch.apis.the_movie_db.services.discover.DiscoverService
import com.doskoch.movies.database.AppDatabase
import com.doskoch.movies.features.splash.SplashFeature
import com.doskoch.movies.features.splash.view.SplashFragmentDirections
import com.extensions.android.functions.isNetworkAvailable
import com.extensions.retrofit.components.service.ServiceConnector

fun appModule(application: Application) = object : AppComponent {
    override val application = application
    override val appDatabase: AppDatabase = AppDatabase.buildDatabase(application)
}

fun theMovieDbApiModule(component: AppComponent) = object : TheMovieDbApi {
    override val isNetworkAvailable: () -> Boolean = { component.application.isNetworkAvailable() }
}

fun splashFeatureModule(component: AppComponent) = object : SplashFeature {
    override val directions: SplashFeature.Directions = object : SplashFeature.Directions {
        override fun toMain() = SplashFragmentDirections.toMain()
    }
}