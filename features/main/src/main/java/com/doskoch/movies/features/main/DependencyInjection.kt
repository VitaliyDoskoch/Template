package com.doskoch.movies.features.main

import androidx.fragment.app.Fragment
import com.extensions.kotlin.components.di.ComponentInjector

internal typealias Component = MainFeatureComponent
internal typealias Injector = MainFeatureInjector

interface MainFeatureComponent {
    val provideAllFilmsFragment: () -> Fragment
    val provideFavouriteFilmsFragment: () -> Fragment
}

object MainFeatureInjector : ComponentInjector<Component>()