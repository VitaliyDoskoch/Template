package com.doskoch.movies.features.splash

import androidx.navigation.NavDirections
import com.extensions.kotlin.components.di.ComponentInjector

interface SplashFeature {

    interface Directions {
        fun toMain(): NavDirections
    }

    val directions: Directions
}

object SplashFeatureInjector : ComponentInjector<SplashFeature>()

internal val Injector
    get() = SplashFeatureInjector.componentProvider!!.value