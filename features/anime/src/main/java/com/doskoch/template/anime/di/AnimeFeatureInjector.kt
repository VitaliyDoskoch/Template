package com.doskoch.template.anime.di

import com.doskoch.template.core.components.kotlin.DestroyableLazy

object AnimeFeatureInjector {
    var component: DestroyableLazy<AnimeFeatureComponent>? = null
}

internal val Injector: AnimeFeatureComponent
    get() = AnimeFeatureInjector.component!!.value
