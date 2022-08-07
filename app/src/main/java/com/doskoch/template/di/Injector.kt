package com.doskoch.template.di

import android.app.Application
import com.doskoch.legacy.kotlin.DestroyableLazy
import com.doskoch.template.GlobalErrorHandlerHolder
import com.doskoch.template.api.jikan.JikanApiInjector
import com.doskoch.template.authorization.AuthorizationFeatureInjector
import com.doskoch.template.database.AppDatabase
import com.doskoch.template.features.splash.SplashFeatureInjector
import com.doskoch.template.navigation.MainNavigator
import timber.log.Timber

interface AppComponent {
    val application: Application
    val navigator: MainNavigator
    val globalErrorHandlerHolder: GlobalErrorHandlerHolder
    val appDatabase: AppDatabase
}

object AppInjector {

    lateinit var component: AppComponent

    fun init(application: Application) {
        component = appModule(application).also(this::logCreation)

        JikanApiInjector.component = jikanApiModule(component).also(this::logCreation)

        SplashFeatureInjector.provider = DestroyableLazy(
            initialize = { splashFeatureModule(component).also(this::logCreation) },
            onDestroyInstance = this::logDestruction
        )

        AuthorizationFeatureInjector.provider = DestroyableLazy(
            initialize = { authorizationFeatureModule(component).also(this::logCreation) },
            onDestroyInstance = this::logDestruction
        )
    }

    @Suppress("UNUSED_PARAMETER")
    private inline fun <reified M> logCreation(module: M) {
        Timber.i("Creating ${M::class.java.simpleName}")
    }

    private inline fun <reified M> logDestruction(module: M?) {
        if (module == null) {
            Timber.e("Trying to destroy ${M::class.java.simpleName}, but it is already destroyed")
        } else {
            Timber.i("Destroying ${M::class.java.simpleName}")
        }
    }
}
