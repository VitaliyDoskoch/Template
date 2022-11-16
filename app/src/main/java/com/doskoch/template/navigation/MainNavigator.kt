package com.doskoch.template.navigation

import com.doskoch.template.anime.di.AnimeFeatureInjector
import com.doskoch.template.auth.di.AuthFeatureInjector
import com.doskoch.template.core.components.event.EventQueue
import com.doskoch.template.core.components.navigation.CoreNavigator
import com.doskoch.template.core.components.navigation.NavAction
import com.doskoch.template.core.components.navigation.NavigationNode
import com.doskoch.template.splash.di.SplashFeatureInjector

class MainNavigator : CoreNavigator {

    override val events = EventQueue<NavAction>()
    override val startNode: NavigationNode = Node.Splash

    fun toAuthFromSplash() = events.enqueue {
        navigate(Node.Auth.route) { popUpTo(Node.Splash.route) { inclusive = true } }
        SplashFeatureInjector.component?.destroyInstance()
    }

    fun toAnimeFromSplash() = events.enqueue {
        navigate(Node.Anime.route) { popUpTo(Node.Splash.route) { inclusive = true } }
        SplashFeatureInjector.component?.destroyInstance()
    }

    fun toAnimeFromAuth() = events.enqueue {
        navigate(Node.Anime.route) { popUpTo(Node.Auth.route) { inclusive = true } }
        AuthFeatureInjector.component?.destroyInstance()
    }

    fun toSplashFromAnime() = events.enqueue {
        navigate(Node.Splash.route) { popUpTo(Node.Anime.route) { inclusive = true } }
        AnimeFeatureInjector.component?.destroyInstance()
    }
}
