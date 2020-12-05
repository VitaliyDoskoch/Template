package com.doskoch.movies.dependencyInjection.modules.features

import com.doskoch.movies.dependencyInjection.AppComponent
import com.doskoch.movies.features.splash.SplashFeature
import com.doskoch.movies.features.splash.view.SplashFragmentDirections

class SplashFeatureModule(
    override val directions: SplashFeature.Directions
) : SplashFeature {
    companion object {
        fun create(component: AppComponent) = SplashFeatureModule(
            directions = object : SplashFeature.Directions {
                override fun toMain() = SplashFragmentDirections.toMain()
            }
        )
    }
}