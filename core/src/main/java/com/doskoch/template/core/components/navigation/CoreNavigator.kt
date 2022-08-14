package com.doskoch.template.core.components.navigation

import androidx.navigation.NavController
import com.doskoch.template.core.components.event.EventQueue

typealias NavAction = NavController.() -> Unit

interface CoreNavigator : EventQueue.Producer {
    val events: EventQueue<NavAction>
}