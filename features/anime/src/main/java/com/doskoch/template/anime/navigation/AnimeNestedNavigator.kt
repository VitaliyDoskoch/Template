package com.doskoch.template.anime.navigation

import com.doskoch.template.core.components.event.EventQueue
import com.doskoch.template.core.components.navigation.CoreNavigator
import com.doskoch.template.core.components.navigation.NavAction

class AnimeNestedNavigator : CoreNavigator {

    override val events = EventQueue<NavAction>()

    fun toDetails(animeId: Int) = events.enqueue { navigate(Destination.AnimeDetails.route(listOf(Destination.AnimeDetails.animeId.name to animeId).toMap())) }

    companion object {
        internal val startDestination = Destination.Top
    }
}