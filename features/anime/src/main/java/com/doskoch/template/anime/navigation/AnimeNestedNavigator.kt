package com.doskoch.template.anime.navigation

import com.doskoch.template.core.components.event.EventQueue
import com.doskoch.template.core.components.navigation.CoreNavigator
import com.doskoch.template.core.components.navigation.NavAction

class AnimeNestedNavigator : CoreNavigator {

    override val events = EventQueue<NavAction>()

    fun toDetails(animeId: Int) = events.enqueue {
        navigate(Node.Details.route {
            Node.Details.dummy.set(Dummy("name"))
        })
    }

    companion object {
        internal val startNode = Node.Top
    }
}