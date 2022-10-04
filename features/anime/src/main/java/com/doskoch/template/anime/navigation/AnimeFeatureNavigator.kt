package com.doskoch.template.anime.navigation

import com.doskoch.template.anime.navigation.Node.Top.setValue
import com.doskoch.template.core.components.event.EventQueue
import com.doskoch.template.core.components.navigation.CoreNavigator
import com.doskoch.template.core.components.navigation.NavAction

abstract class AnimeFeatureNavigator : CoreNavigator {

    override val events = EventQueue<NavAction>()

    abstract fun toSplash()

    fun toDetails(animeId: Int) = events.enqueue {
        navigate(Node.Details.buildRoute(
            rBool = true,
            oInt = 100,
            rJson = Dummy("dummy"),
            oString = "non-default"
        ))
    }

    companion object {
        internal val startNode = Node.Top
    }
}