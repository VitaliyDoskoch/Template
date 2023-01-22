package com.doskoch.template.core.android.components.navigation

import androidx.navigation.NavController
import com.doskoch.template.core.android.components.event.EventQueue

typealias NavAction = NavController.() -> Unit

interface CoreNavigator : EventQueue.Producer {
    val events: EventQueue<NavAction>
    val startNode: NavigationNode
}
