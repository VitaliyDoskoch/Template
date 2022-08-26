package com.doskoch.template.anime.navigation

import com.doskoch.template.core.components.event.EventQueue
import com.doskoch.template.core.components.navigation.CoreNavigator
import com.doskoch.template.core.components.navigation.NavAction

class AnimeNestedNavigator : CoreNavigator {

    override val events = EventQueue<NavAction>()

    fun toDetails(animeId: Int) = events.enqueue {
        navigate(Node.Details.route {
            setArgument(Node.Details.first to 1)
            setArgument(Node.Details.second to true)
            setArgument(Node.Details.fourth to 4)
            setArgument(Node.Details.fifth to 5)
            setArgument(Node.Details.sixth to Dummy("name"))
        })
    }

    companion object {
        internal val startNode = Node.Top
    }
}