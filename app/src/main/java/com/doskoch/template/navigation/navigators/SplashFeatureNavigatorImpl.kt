package com.doskoch.template.navigation.navigators

import com.doskoch.template.core.android.components.navigation.NavigationNode
import com.doskoch.template.navigation.MainNavigator
import com.doskoch.template.navigation.Node
import com.doskoch.template.splash.presentation.navigation.SplashFeatureNavigator
import javax.inject.Inject

class SplashFeatureNavigatorImpl @Inject constructor(private val navigator: MainNavigator) : SplashFeatureNavigator() {
    override val startNode: NavigationNode = Node.Splash

    override fun toAuth() = navigator.toAuthFromSplash()
    override fun toAnime() = navigator.toAnimeFromSplash()
}
