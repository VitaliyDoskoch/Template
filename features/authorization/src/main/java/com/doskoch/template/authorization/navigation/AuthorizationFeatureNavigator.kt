package com.doskoch.template.authorization.navigation

import com.doskoch.template.core.components.event.EventQueue
import com.doskoch.template.core.components.navigation.CoreNavigator
import com.doskoch.template.core.components.navigation.NavAction

abstract class AuthorizationFeatureNavigator : CoreNavigator {
    override val events = EventQueue<NavAction>()

    abstract fun toAnime()

    fun navigateUp() = events.enqueue { navigateUp() }

    fun toSignIn() = events.enqueue { navigate(Node.SignIn.route) }

    companion object {
        internal val startNode = Node.SignUp
    }
}
