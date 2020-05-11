package com.doskoch.movies.dependencyInjection

import com.doskoch.movies.R
import com.doskoch.movies.features.main.MainFeatureInjector
import com.doskoch.movies.features.splash.SplashFeatureInjector
import com.extensions.kotlin.components.di.ComponentInjector

enum class ModuleScope(val destinations: Set<Int>, val injector: ComponentInjector<*>) {
    SPLASH(setOf(R.id.splashFragment), SplashFeatureInjector),
    MAIN(setOf(R.id.mainFragment), MainFeatureInjector)
}