package com.doskoch.template.di

import android.app.Application
import com.doskoch.api.the_movie_db.TheMovieDbApi
import com.doskoch.legacy.android.functions.isNetworkAvailable
import com.doskoch.template.database.AppDatabase
import com.doskoch.template.features.splash.SplashFeature

fun appModule(application: Application) = object : AppComponent {
    override val application = application
    override val appDatabase: AppDatabase = AppDatabase.buildDatabase(application)
}

fun theMovieDbApiModule(component: AppComponent) = object : TheMovieDbApi {
    override val isNetworkAvailable: () -> Boolean = { component.application.isNetworkAvailable() }
}

fun splashFeatureModule(component: AppComponent) = object : SplashFeature {
}
