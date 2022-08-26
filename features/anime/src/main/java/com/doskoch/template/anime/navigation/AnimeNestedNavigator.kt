package com.doskoch.template.anime.navigation

import com.doskoch.template.core.components.event.EventQueue
import com.doskoch.template.core.components.navigation.CoreNavigator
import com.doskoch.template.core.components.navigation.NavAction

class AnimeNestedNavigator : CoreNavigator {

    override val events = EventQueue<NavAction>()

    fun toDetails(animeId: Int) = events.enqueue {
        navigate(Node.Details.route {
            Node.Details.requiredBool setTo true
            Node.Details.optionalInt setTo 100

            Node.Details.requiredJson setTo Dummy("dummy")
            Node.Details.optionalString setTo "non-default"
        })
    }

    companion object {
        internal val startNode = Node.Top
    }
}