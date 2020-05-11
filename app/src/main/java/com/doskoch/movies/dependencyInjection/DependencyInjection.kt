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

internal typealias Component = AppComponent
internal typealias Injector = AppInjector

interface AppComponent {
    val application: Application
    val appDatabase: AppDatabase
}

object AppInjector {

    lateinit var component: AppComponent

    fun init(application: Application) {
        component = AppModule.create(application)

        CoreInjector.component = CoreModule.create(component)
            .also { logCreation<CoreModule>() }

        TheMovieDbInjector.component = TheMovieDbApiModule.create(component)
            .also { logCreation<TheMovieDbApiModule>() }

        SplashFeatureInjector.componentProvider = DestroyableLazy(
            { SplashFeatureModule.create(component).also { logCreation<SplashFeatureModule>() } },
            { logDestruction<SplashFeatureModule>() }
        )
        MainFeatureInjector.componentProvider = DestroyableLazy(
            { MainFeatureModule.create(component).also { logCreation<MainFeatureModule>() } },
            {
                destroyMainFeatureSubmodules()
                logDestruction<MainFeatureModule>()
            }
        ).also { initMainFeatureSubmodules() }
    }

    private fun initMainFeatureSubmodules() {
        AllFilmsFeatureInjector.componentProvider = DestroyableLazy(
            {
                AllFilmsFeatureModule.create(component)
                    .also { logCreation<AllFilmsFeatureModule>() }
            },
            { logDestruction<AllFilmsFeatureModule>() }
        )
        FavouriteFilmsFeatureInjector.componentProvider = DestroyableLazy(
            {
                FavouriteFilmsFeatureModule.create(component)
                    .also { logCreation<FavouriteFilmsFeatureModule>() }
            },
            { logDestruction<FavouriteFilmsFeatureModule>() }
        )
    }

    private fun destroyMainFeatureSubmodules() {
        AllFilmsFeatureInjector.componentProvider?.destroyInstance()
        FavouriteFilmsFeatureInjector.componentProvider?.destroyInstance()
    }

    private inline fun <reified M : Any> logCreation() {
        Timber.v("Creating ${M::class.java.simpleName}")
    }

    private inline fun <reified M : Any> logDestruction() {
        Timber.v("Destroying ${M::class.java.simpleName}")
    }
}