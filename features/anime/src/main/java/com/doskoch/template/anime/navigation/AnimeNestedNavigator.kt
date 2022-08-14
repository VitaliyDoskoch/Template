package com.doskoch.template.anime.navigation

import com.doskoch.template.core.components.event.EventQueue
import com.doskoch.template.core.components.navigation.CoreNavigator
import com.doskoch.template.core.components.navigation.NavAction

class AnimeNestedNavigator : CoreNavigator {

    override val events = EventQueue<NavAction>()

    fun navigateUp() = events.enqueue { navigateUp() }

    companion object {
        internal val startDestination = Destinations.Top
    }
}