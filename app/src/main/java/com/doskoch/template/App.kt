package com.doskoch.template

import android.app.Application
import com.doskoch.template.auth.di.AuthFeatureComponent
import com.doskoch.template.di.AppInjector
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var appInjector: AppInjector

    override fun onCreate() {
        super.onCreate()

        initLogging()

        appInjector.init(this)
    }

    private fun initLogging() {
        if (BuildConfig.isLoggingEnabled) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
