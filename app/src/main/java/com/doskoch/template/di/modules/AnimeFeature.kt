package com.doskoch.template.di.modules

import com.doskoch.template.anime.AnimeFeature
import com.doskoch.template.anime.AnimeFeatureNavigator
import com.doskoch.template.anime.AnimeNestedNavigator
import com.doskoch.template.di.AppComponent

fun animeFeatureModule(component: AppComponent) = object : AnimeFeature {
    override val navigator = object : AnimeFeatureNavigator {

    }

    override val nestedNavigator = AnimeNestedNavigator()

    override val globalErrorHandler = component.globalErrorHandlerHolder.handler
}