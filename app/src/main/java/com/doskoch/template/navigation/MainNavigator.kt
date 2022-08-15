package com.doskoch.template.navigation

import androidx.navigation.NavOptions
import com.doskoch.template.core.components.event.EventQueue
import com.doskoch.template.core.components.navigation.CoreNavigator
import com.doskoch.template.core.components.navigation.NavAction

class MainNavigator : CoreNavigator {

    override val events = EventQueue<NavAction>()

    fun toAuthorization(options: NavOptions) = events.enqueue { navigate(Destinations.Authorization.name, options) }

    fun toAnime(options: NavOptions) = events.enqueue { navigate(Destinations.Anime.name, options) }

    fun toSplash(options: NavOptions) = events.enqueue { navigate(Destinations.Splash.name, options) }

    companion object {
        val startDestination = Destinations.Splash
    }
}