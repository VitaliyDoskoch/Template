package com.doskoch.template.anime.presentation.navigation

import com.doskoch.template.core.android.components.event.EventQueue
import com.doskoch.template.core.android.components.navigation.CoreNavigator
import com.doskoch.template.core.android.components.navigation.NavAction
import com.doskoch.template.core.android.components.navigation.NavigationNode

abstract class AnimeFeatureNavigator : CoreNavigator {

    override val events = EventQueue<NavAction>()
    override val startNode: NavigationNode = Node.Top

    abstract fun toSplash()

    fun navigateUp() = events.enqueue { navigateUp() }

    fun toFavorite() = events.enqueue { navigate(Node.Favorite.route) }

    fun toDetails(animeId: Int, title: String) = events.enqueue { navigate(Node.Details.buildRoute(animeId, title)) }
}
