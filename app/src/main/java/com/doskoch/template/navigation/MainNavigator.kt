package com.doskoch.template.navigation

import com.doskoch.template.anime.di.AnimeFeatureComponentAccessor
import com.doskoch.template.auth.presentation.di.AuthFeatureComponentAccessor
import com.doskoch.template.core.android.components.event.EventQueue
import com.doskoch.template.core.android.components.navigation.CoreNavigator
import com.doskoch.template.core.android.components.navigation.NavAction
import com.doskoch.template.core.android.components.navigation.NavigationNode
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainNavigator @Inject constructor() : CoreNavigator {

    override val events = EventQueue<NavAction>()
    override val startNode: NavigationNode = Node.Splash

    fun toAuthFromSplash() = events.enqueue {
        navigate(Node.Auth.route) { popUpTo(Node.Splash.route) { inclusive = true } }
    }

    fun toAnimeFromSplash() = events.enqueue {
        navigate(Node.Anime.route) { popUpTo(Node.Splash.route) { inclusive = true } }
    }

    fun toAnimeFromAuth() = events.enqueue {
        navigate(Node.Anime.route) { popUpTo(Node.Auth.route) { inclusive = true } }
        AuthFeatureComponentAccessor.destroyComponent()
    }

    fun toSplashFromAnime() = events.enqueue {
        navigate(Node.Splash.route) { popUpTo(Node.Anime.route) { inclusive = true } }
        AnimeFeatureComponentAccessor.destroyComponent()
    }
}
