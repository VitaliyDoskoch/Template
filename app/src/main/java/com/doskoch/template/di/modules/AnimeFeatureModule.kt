package com.doskoch.template.di.modules

import com.doskoch.template.anime.di.AnimeFeatureComponent
import com.doskoch.template.anime.navigation.AnimeFeatureNavigator
import com.doskoch.template.api.jikan.di.JikanApiModule
import com.doskoch.template.di.AppComponent
import dagger.hilt.DefineComponent
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Scope
import javax.inject.Singleton

fun animeFeatureModule(component: AppComponent) = object : AnimeFeatureComponent {
    override val navigator = object : AnimeFeatureNavigator() {
        override fun toSplash() = component.navigator.toSplashFromAnime()
    }

    override val globalErrorHandler = component.globalErrorHandler

    override val topService = JikanApiModule.topService

    override val dbAnimeDao = component.appDatabase.dbAnimeDao()

    override val animeService = JikanApiModule.animeService
}

@Scope
@MustBeDocumented
@Retention(value = AnnotationRetention.RUNTIME)
annotation class FeatureScope

@FeatureScope
@DefineComponent(parent = SingletonComponent::class)
interface FeatureComponent {

    @DefineComponent.Builder
    interface Builder {
        fun build(): FeatureComponent
    }

    @EntryPoint
    @InstallIn(FeatureComponent::class)
    interface FeatureEntryPoint {
        fun getRandomInt(): Int
    }
}

@Singleton
class Manager @Inject constructor(
    private val provider: Provider<FeatureComponent.Builder>
) {
    var component: FeatureComponent? = null
        private set

    private fun createComponent() {
        component = provider.get().build()
    }

    private fun destroyComponent() {
        component = null
    }
}

fun access(manager: Manager) {
    EntryPoints.get(manager.component!!, FeatureComponent.FeatureEntryPoint::class.java).getRandomInt()
}