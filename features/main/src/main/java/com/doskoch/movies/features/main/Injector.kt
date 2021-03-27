package com.doskoch.movies.features.main

import androidx.fragment.app.Fragment
import com.extensions.kotlin.components.di.ComponentInjector

interface MainFeature {
    val provideAllFilmsFragment: () -> Fragment
    val provideFavouriteFilmsFragment: () -> Fragment
}

object MainFeatureInjector : ComponentInjector<MainFeature>()

internal val Injector
    get() = MainFeatureInjector.componentProvider!!.value