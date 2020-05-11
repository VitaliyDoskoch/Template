package com.doskoch.movies.features.splash

import androidx.navigation.NavDirections
import com.extensions.kotlin.components.di.ComponentInjector

internal typealias Component = SplashFeatureComponent
internal typealias Directions = SplashFeatureDirections
internal typealias Injector = SplashFeatureInjector

interface SplashFeatureComponent {
    val directions: Directions
}

interface SplashFeatureDirections {
    fun toMain(): NavDirections
}

object SplashFeatureInjector : ComponentInjector<Component>()