package com.doskoch.template.navigation

import com.doskoch.template.core.components.event.EventQueue
import com.doskoch.template.core.components.navigation.CoreNavigator
import com.doskoch.template.core.components.navigation.NavAction
import com.doskoch.template.core.components.navigation.NavigationNode

class MainNavigator : CoreNavigator {

    override val events = EventQueue<NavAction>()
    override val startNode: NavigationNode = Node.Splash

    fun toAuth() = events.enqueue {
        navigate(Node.Auth.route) { popUpTo(Node.Splash.route) { inclusive = true } }
    }

    fun toAnimeFromSplash() = events.enqueue {
        navigate(Node.Anime.route) { popUpTo(Node.Splash.route) { inclusive = true } }
    }

    fun toAnimeFromAuth() = events.enqueue {
        navigate(Node.Anime.route) { popUpTo(Node.Auth.route) { inclusive = true } }
    }

    fun toSplash() = events.enqueue {
        navigate(Node.Splash.route) { popUpTo(Node.Anime.route) { inclusive = true } }
    }
}
