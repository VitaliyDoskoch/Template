package com.doskoch.template.anime.di

import com.doskoch.legacy.kotlin.DestroyableLazy
import com.doskoch.template.anime.top.TopAnimeViewModel

object AnimeFeatureInjector {
    var provider: DestroyableLazy<AnimeFeature>? = null
}

internal val Injector: AnimeFeature
    get() = AnimeFeatureInjector.provider!!.value

object Module {

    val topAnimeViewModel: TopAnimeViewModel
        get() = TopAnimeViewModel()

}