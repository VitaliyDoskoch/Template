package com.doskoch.template.splash.presentation.navigation

import com.doskoch.template.core.android.components.event.EventQueue
import com.doskoch.template.core.android.components.navigation.CoreNavigator
import com.doskoch.template.core.android.components.navigation.NavAction

abstract class SplashFeatureNavigator : CoreNavigator {
    override val events = EventQueue<NavAction>()

    abstract fun toAuth()
    abstract fun toAnime()
}
