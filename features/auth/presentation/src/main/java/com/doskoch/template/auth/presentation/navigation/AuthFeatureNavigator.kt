package com.doskoch.template.auth.presentation.navigation

import com.doskoch.template.core.android.components.event.EventQueue
import com.doskoch.template.core.android.components.navigation.CoreNavigator
import com.doskoch.template.core.android.components.navigation.NavAction
import com.doskoch.template.core.android.components.navigation.NavigationNode

abstract class AuthFeatureNavigator : CoreNavigator {

    override val events = EventQueue<NavAction>()
    override val startNode: NavigationNode = Node.SignUp

    abstract fun toAnime()

    fun navigateUp() = events.enqueue { navigateUp() }

    fun toSignIn() = events.enqueue { navigate(Node.SignIn.route) }
}
