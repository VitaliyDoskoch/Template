package com.doskoch.template.anime.di

import com.doskoch.template.core.components.kotlin.DestroyableLazy

object AnimeFeatureInjector {
    var provider: DestroyableLazy<AnimeFeatureComponent>? = null
}

internal val Injector: AnimeFeatureComponent
    get() = AnimeFeatureInjector.provider!!.value
