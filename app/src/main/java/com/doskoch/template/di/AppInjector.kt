package com.doskoch.template.di

import com.doskoch.template.anime.di.AnimeFeatureComponent
import com.doskoch.template.anime.di.AnimeFeatureComponentAccessor
import com.doskoch.template.anime.di.AnimeFeatureInjector
import com.doskoch.template.auth.presentation.di.AuthFeatureComponent
import com.doskoch.template.auth.presentation.di.AuthFeatureComponentAccessor
import com.doskoch.template.auth.presentation.di.AuthFeatureInjector
import com.doskoch.template.core.android.components.error.ErrorResponseParser
import com.doskoch.template.core.android.di.CoreAndroidComponent
import com.doskoch.template.core.android.di.CoreAndroidComponentAccessor
import com.doskoch.template.core.kotlin.lazy.DestroyableLazy
import com.doskoch.template.error.ErrorResponseParserImpl
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class AppInjector @Inject constructor(
    private val coreAndroidComponentBuilder: Provider<CoreAndroidComponent.Builder>,
    private val authFeatureComponentBuilder: Provider<AuthFeatureComponent.Builder>,
    private val animeFeatureComponentBuilder: Provider<AnimeFeatureComponent.Builder>
) {

    fun init() {
        CoreAndroidComponentAccessor.init(
            DestroyableLazy(
                initialize = { coreAndroidComponentBuilder.get().build().also(this::logCreation) },
                onDestroyInstance = this::logDestruction
            )
        )

        AuthFeatureComponentAccessor.init(
            DestroyableLazy(
                initialize = { authFeatureComponentBuilder.get().build().also(this::logCreation) },
                onDestroyInstance = this::logDestruction
            )
        )

        AnimeFeatureComponentAccessor.init(
            DestroyableLazy(
                initialize = { animeFeatureComponentBuilder.get().build().also(this::logCreation) },
                onDestroyInstance = this::logDestruction
            )
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
