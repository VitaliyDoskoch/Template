package com.doskoch.template.navigation

import androidx.navigation.NavOptions
import com.doskoch.template.core.components.event.EventQueue
import com.doskoch.template.core.components.navigation.CoreNavigator
import com.doskoch.template.core.components.navigation.NavAction
import com.doskoch.template.core.components.navigation.NavigationNode

class MainNavigator : CoreNavigator {

    override val events = EventQueue<NavAction>()
    override val startNode: NavigationNode = Node.Splash

    fun toAuthorization(options: NavOptions) = events.enqueue { navigate(Node.Authorization.route, options) }

    fun toAnime(options: NavOptions) = events.enqueue { navigate(Node.Anime.route, options) }

    fun toSplash(options: NavOptions) = events.enqueue { navigate(Node.Splash.route, options) }
}
