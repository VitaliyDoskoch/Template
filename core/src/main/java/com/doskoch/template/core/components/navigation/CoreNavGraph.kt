package com.doskoch.template.core.components.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.EntryPointAccessors

@Composable
inline fun <reified T> CoreNavGraph(
    crossinline navigator: (T) -> CoreNavigator,
    modifier: Modifier = Modifier,
    noinline builder: NavGraphBuilder.() -> Unit
) {
    val context = LocalContext.current.applicationContext
    val navigator = remember { EntryPointAccessors.fromApplication(context, T::class.java).let(navigator) }

    CoreNavGraph(navigator = navigator, modifier = modifier, builder = builder)
}

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
