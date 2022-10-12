package com.doskoch.template.anime.navigation

import com.doskoch.template.anime.navigation.Node.Top.setValue
import com.doskoch.template.core.components.event.EventQueue
import com.doskoch.template.core.components.navigation.CoreNavigator
import com.doskoch.template.core.components.navigation.NavAction

abstract class AnimeFeatureNavigator : CoreNavigator {

    override val events = EventQueue<NavAction>()

    abstract fun toSplash()

    fun toFavorite() = events.enqueue { navigate(Node.Favorite.route) }

    fun toDetails(animeId: Int) = events.enqueue { navigate(Node.Details.buildRoute(animeId = animeId)) }

    companion object {
        internal val startNode = Node.Top
    }
}