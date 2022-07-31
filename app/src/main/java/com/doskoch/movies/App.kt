package com.doskoch.movies

import android.app.Application
import com.doskoch.movies.di.AppInjector
import timber.log.Timber

@Suppress("unused")
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initLogging()

        AppInjector.init(this)
    }

    private fun initLogging() {
        if (BuildConfig.is_logging_enabled) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
