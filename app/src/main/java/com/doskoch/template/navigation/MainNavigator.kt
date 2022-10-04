package com.doskoch.template.navigation

import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import com.doskoch.template.core.components.event.EventQueue
import com.doskoch.template.core.components.navigation.CoreNavigator
import com.doskoch.template.core.components.navigation.NavAction

class MainNavigator : CoreNavigator {

    override val events = EventQueue<NavAction>()

    fun toAuthorization(options: NavOptions) = events.enqueue { navigate(Node.Authorization.route, options) }

    fun toAnime(options: NavOptions) = events.enqueue { navigate(Node.Anime.route, options) }

    fun toSplash(options: NavOptions) = events.enqueue { navigate(Node.Splash.route, options) }

    companion object {
        internal val startDestination = Node.Splash
    }
}