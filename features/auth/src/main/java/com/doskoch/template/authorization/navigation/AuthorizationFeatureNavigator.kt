package com.doskoch.template.authorization.navigation

import com.doskoch.template.core.components.event.EventQueue
import com.doskoch.template.core.components.navigation.CoreNavigator
import com.doskoch.template.core.components.navigation.NavAction
import com.doskoch.template.core.components.navigation.NavigationNode

abstract class AuthorizationFeatureNavigator : CoreNavigator {

    override val events = EventQueue<NavAction>()
    override val startNode: NavigationNode = Node.SignUp

    abstract fun toAnime()

    fun navigateUp() = events.enqueue { navigateUp() }

    fun toSignIn() = events.enqueue { navigate(Node.SignIn.route) }
}
