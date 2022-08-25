package com.doskoch.template.anime.navigation

import com.doskoch.template.core.components.event.EventQueue
import com.doskoch.template.core.components.navigation.CoreNavigator
import com.doskoch.template.core.components.navigation.NavAction

class AnimeNestedNavigator : CoreNavigator {

    override val events = EventQueue<NavAction>()

    fun toDetails(animeId: Int) = events.enqueue {
        navigate(Node.Details.route {
            add(Node.Details.first to 1)
            add(Node.Details.second to true)
            add(Node.Details.fourth to 4)
            add(Node.Details.fifth to 5)
        })
    }

    companion object {
        internal val startNode = Node.Top
    }
}