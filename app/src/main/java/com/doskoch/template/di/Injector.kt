package com.doskoch.template.di

import android.app.Application
import com.doskoch.api.the_movie_db.TheMovieDbApiInjector
import com.doskoch.legacy.kotlin.DestroyableLazy
import com.doskoch.template.database.AppDatabase
import com.doskoch.template.features.splash.SplashFeatureInjector
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

        SplashFeatureInjector.provider = DestroyableLazy(
            { splashFeatureModule(component).also(this::logCreation) },
            this::logDestruction
        )
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
