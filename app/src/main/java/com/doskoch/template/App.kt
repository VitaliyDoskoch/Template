package com.doskoch.template

import android.app.Application
import com.doskoch.template.di.AppInjector
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initLogging()

        AppInjector.init(this)
    }

    private fun initLogging() {
        if (BuildConfig.isLoggingEnabled) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
