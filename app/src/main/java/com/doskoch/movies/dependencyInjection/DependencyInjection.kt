package com.doskoch.movies.dependencyInjection

import android.app.Application
import com.doskoch.apis.the_movie_db.TheMovieDbInjector
import com.doskoch.movies.core.CoreInjector
import com.doskoch.movies.database.AppDatabase
import com.doskoch.movies.dependencyInjection.modules.AppModule
import com.doskoch.movies.dependencyInjection.modules.CoreModule
import com.doskoch.movies.dependencyInjection.modules.apis.TheMovieDbApiModule
import com.doskoch.movies.dependencyInjection.modules.features.AllFilmsFeatureModule
import com.doskoch.movies.dependencyInjection.modules.features.FavouriteFilmsFeatureModule
import com.doskoch.movies.dependencyInjection.modules.features.MainFeatureModule
import com.doskoch.movies.dependencyInjection.modules.features.SplashFeatureModule
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
        component = AppModule.create(application).also(this::logCreation)

        CoreInjector.component = CoreModule.create(component).also(this::logCreation)

        TheMovieDbInjector.component = TheMovieDbApiModule.create(component).also(this::logCreation)

        SplashFeatureInjector.componentProvider = DestroyableLazy(
            { SplashFeatureModule.create(component).also(this::logCreation) },
            this::logDestruction
        )
        MainFeatureInjector.componentProvider = DestroyableLazy(
            { MainFeatureModule.create(component).also(this::logCreation) },
            {
                destroyMainFeatureSubmodules()
                logDestruction(it)
            }
        ).also { initMainFeatureSubmodules() }
    }

    private fun initMainFeatureSubmodules() {
        AllFilmsFeatureInjector.componentProvider = DestroyableLazy(
            { AllFilmsFeatureModule.create(component).also(this::logCreation) },
            this::logDestruction
        )
        FavouriteFilmsFeatureInjector.componentProvider = DestroyableLazy(
            { FavouriteFilmsFeatureModule.create(component).also(this::logCreation) },
            this::logDestruction
        )
    }

    private fun destroyMainFeatureSubmodules() {
        AllFilmsFeatureInjector.componentProvider?.destroyInstance()
        FavouriteFilmsFeatureInjector.componentProvider?.destroyInstance()
    }

    private inline fun <reified M> logCreation(module: M) {
        Timber.i("Creating ${M::class.java.simpleName}")
    }

    private inline fun <reified M> logDestruction(module: M?) {
        if (module == null) {
            Timber.i("Trying to destroy ${M::class.java.simpleName}, but it is already destroyed")
        } else {
            Timber.i("Destroying ${M::class.java.simpleName}")
        }
    }
}