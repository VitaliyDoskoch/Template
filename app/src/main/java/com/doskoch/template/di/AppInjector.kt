package com.doskoch.template.di

import com.doskoch.template.anime.di.AnimeFeatureComponent
import com.doskoch.template.anime.di.AnimeFeatureInjector
import com.doskoch.template.auth.di.AuthFeatureComponent
import com.doskoch.template.auth.di.AuthFeatureInjector
import com.doskoch.template.core.components.kotlin.DestroyableLazy
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class AppInjector @Inject constructor(
    private val authFeatureComponentBuilder: Provider<AuthFeatureComponent.Builder>,
    private val animeFeatureComponentBuilder: Provider<AnimeFeatureComponent.Builder>
) {

    fun init() {
        AuthFeatureInjector.component = DestroyableLazy(
            initialize = { authFeatureComponentBuilder.get().build().also(this::logCreation) },
            onDestroyInstance = this::logDestruction
        )

        AnimeFeatureInjector.component = DestroyableLazy(
            initialize = { animeFeatureComponentBuilder.get().build().also(this::logCreation) },
            onDestroyInstance = this::logDestruction
        )

//        CoreInjector.component = coreModule(component).also(this::logCreation)
//        JikanApiInjector.component = jikanApiModule(component).also(this::logCreation)
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
