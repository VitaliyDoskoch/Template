package com.doskoch.movies.di

import android.app.Application
import com.doskoch.apis.the_movie_db.TheMovieDbApiInjector
import com.doskoch.movies.database.AppDatabase
import com.doskoch.movies.features.films_all.AllFilmsFeatureInjector
import com.doskoch.movies.features.films_favourite.FavouriteFilmsFeatureInjector
import com.doskoch.movies.features.main.MainFeatureInjector
import com.doskoch.movies.features.splash.SplashFeatureInjector
import com.extensions.kotlin.components.DestroyableLazy
import timber.log.Timber

interface AppComponent {
    val application: Application
    val appDatabase: AppDatabase
}

object AppInjector {

    lateinit var component: AppComponent

    fun init(application: Application) {
        component = appModule(application).also(this::logCreation)

        TheMovieDbApiInjector.component = theMovieDbApiModule(component).also(this::logCreation)

        SplashFeatureInjector.componentProvider = DestroyableLazy(
            { splashFeatureModule(component).also(this::logCreation) },
            this::logDestruction
        )
        MainFeatureInjector.componentProvider = DestroyableLazy(
            { mainFeatureModule(component).also(this::logCreation) },
            {
                destroyMainFeatureSubmodules()
                logDestruction(it)
            }
        ).also { initMainFeatureSubmodules() }
    }

    private fun initMainFeatureSubmodules() {
        AllFilmsFeatureInjector.componentProvider = DestroyableLazy(
            { allFilmsFeatureModule(component).also(this::logCreation) },
            this::logDestruction
        )
        FavouriteFilmsFeatureInjector.componentProvider = DestroyableLazy(
            { favouriteFilmsFeatureModule(component).also(this::logCreation) },
            this::logDestruction
        )
    }

    private fun destroyMainFeatureSubmodules() {
        AllFilmsFeatureInjector.componentProvider?.destroyInstance()
        FavouriteFilmsFeatureInjector.componentProvider?.destroyInstance()
    }

    private inline fun <reified M> logCreation(module: M) {
        Timber.e("Creating ${M::class.java.simpleName}")
    }

    private inline fun <reified M> logDestruction(module: M?) {
        if (module == null) {
            Timber.i("Trying to destroy ${M::class.java.simpleName}, but it is already destroyed")
        } else {
            Timber.i("Destroying ${M::class.java.simpleName}")
        }
    }
}