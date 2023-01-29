package com.doskoch.template.navigation.navigators

import com.doskoch.template.anime.presentation.navigation.AnimeFeatureNavigator
import com.doskoch.template.navigation.MainNavigator
import javax.inject.Inject

class AnimeFeatureNavigatorImpl @Inject constructor(private val navigator: MainNavigator) : AnimeFeatureNavigator() {
    override fun toSplash() = navigator.toSplashFromAnime()
}
