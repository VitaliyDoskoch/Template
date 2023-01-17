package com.doskoch.template.di

import android.app.Application
import com.doskoch.template.anime.di.AnimeFeatureInjector
import com.doskoch.template.api.jikan.di.JikanApiInjector
import com.doskoch.template.auth.di.AuthFeatureComponent
import com.doskoch.template.auth.di.authFeatureComponentHolder
import com.doskoch.template.core.components.kotlin.DestroyableLazy
import com.doskoch.template.core.di.CoreInjector
import com.doskoch.template.di.modules.animeFeatureModule
import com.doskoch.template.di.modules.appModule
import com.doskoch.template.di.modules.coreModule
import com.doskoch.template.di.modules.jikanApiModule
import dagger.hilt.android.EntryPointAccessors
import timber.log.Timber

object AppInjector {

    lateinit var component: AppComponent

    fun init(application: Application) {
        component = appModule(application).also(this::logCreation)

        val builder = EntryPointAccessors.fromApplication(application, AuthFeatureComponent.BuilderEntryPoint::class.java).provideBuilder()
        authFeatureComponentHolder = DestroyableLazy(
            initialize = { builder.build().also(this::logCreation) },
            onDestroyInstance = this::logDestruction
        )

        CoreInjector.component = coreModule(component).also(this::logCreation)
        JikanApiInjector.component = jikanApiModule(component).also(this::logCreation)

        AnimeFeatureInjector.component = DestroyableLazy(
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
