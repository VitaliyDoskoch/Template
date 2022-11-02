package com.doskoch.template.navigation

import com.doskoch.template.core.components.event.EventQueue
import com.doskoch.template.core.components.navigation.CoreNavigator
import com.doskoch.template.core.components.navigation.NavAction
import com.doskoch.template.core.components.navigation.NavigationNode

class MainNavigator : CoreNavigator {

    override val events = EventQueue<NavAction>()
    override val startNode: NavigationNode = Node.Splash

    fun toAuthorization() = events.enqueue {
        navigate(Node.Authorization.route) { popUpTo(Node.Splash.route) { inclusive = true } }
    }

    fun toAnimeFromSplash() = events.enqueue {
        navigate(Node.Anime.route) { popUpTo(Node.Splash.route) { inclusive = true } }
    }

    fun toAnimeFromAuthorization() = events.enqueue {
        navigate(Node.Anime.route) { popUpTo(Node.Authorization.route) { inclusive = true } }
    }

    fun toSplash() = events.enqueue {
        navigate(Node.Splash.route) { popUpTo(Node.Anime.route) { inclusive = true } }
    }
}
