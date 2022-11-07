package com.doskoch.template.di

import android.app.Application
import com.doskoch.template.anime.di.AnimeFeatureInjector
import com.doskoch.template.api.jikan.di.JikanApiInjector
import com.doskoch.template.authorization.di.AuthorizationFeatureInjector
import com.doskoch.template.core.components.kotlin.DestroyableLazy
import com.doskoch.template.core.di.CoreInjector
import com.doskoch.template.di.modules.animeFeatureModule
import com.doskoch.template.di.modules.appModule
import com.doskoch.template.di.modules.authorizationFeatureModule
import com.doskoch.template.di.modules.coreModule
import com.doskoch.template.di.modules.jikanApiModule
import com.doskoch.template.di.modules.splashFeatureModule
import com.doskoch.template.splash.di.SplashFeatureInjector
import timber.log.Timber

object AppInjector {

    lateinit var component: AppComponent

    fun init(application: Application) {
        component = appModule(application).also(this::logCreation)

        CoreInjector.component = coreModule(component).also(this::logCreation)
        JikanApiInjector.component = jikanApiModule(component).also(this::logCreation)

        SplashFeatureInjector.provider = DestroyableLazy(
            initialize = { splashFeatureModule(component).also(this::logCreation) },
            onDestroyInstance = this::logDestruction
        )

        AuthorizationFeatureInjector.provider = DestroyableLazy(
            initialize = { authorizationFeatureModule(component).also(this::logCreation) },
            onDestroyInstance = this::logDestruction
        )

        AnimeFeatureInjector.provider = DestroyableLazy(
            initialize = { animeFeatureModule(component).also(this::logCreation) },
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
