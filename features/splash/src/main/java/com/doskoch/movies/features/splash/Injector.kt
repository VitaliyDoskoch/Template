package com.doskoch.movies.features.splash

import androidx.navigation.NavDirections
import com.doskoch.legacy.kotlin.DestroyableLazy

interface SplashFeature {
    interface Directions {
        fun toMain(): NavDirections
    }

    val directions: Directions
}

object SplashFeatureProvider {
    var provider: DestroyableLazy<SplashFeature>? = null
}

internal val Injector
    get() = SplashFeatureProvider.provider!!.value