package com.doskoch.template.authorization.navigation

import com.doskoch.template.core.components.event.EventQueue
import com.doskoch.template.core.components.navigation.CoreNavigator
import com.doskoch.template.core.components.navigation.NavAction

class AuthorizationNestedNavigator : CoreNavigator {

    override val events = EventQueue<NavAction>()

    fun navigateUp() = events.enqueue { navigateUp() }

    fun toSignIn() = events.enqueue { navigate(Destinations.SignIn.name) }

    companion object {
        internal val startDestination = Destinations.SignUp
    }
}