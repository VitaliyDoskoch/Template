package com.doskoch.template.core.components.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun CoreNavGraph(
    navigator: CoreNavigator,
    modifier: Modifier = Modifier,
    builder: NavGraphBuilder.() -> Unit
) {
    val navController = rememberNavController()

    LaunchedEffect(navController) {
        navigator.events.collect { it.invoke(navController) }
    }

    NavHost(
        navController = navController,
        startDestination = navigator.startNode.route,
        modifier = modifier,
        builder = builder
    )
}