package com.doskoch.template.anime.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.doskoch.template.anime.di.Injector
import com.doskoch.template.anime.screens.details.AnimeDetailsScreen
import com.doskoch.template.anime.screens.top.TopAnimeScreen
import com.doskoch.template.core.components.navigation.JsonNavType
import com.doskoch.template.core.components.navigation.NavigationNode
import com.doskoch.template.core.components.navigation.composable
import com.doskoch.template.core.components.navigation.typedArgument
import timber.log.Timber

@Composable
fun AnimeNestedNavGraph() {
    val navController = rememberNavController()

    LaunchedEffect(navController) {
        Injector.nestedNavigator.events.collect { it.invoke(navController) }
    }

    NavHost(navController = navController, startDestination = AnimeNestedNavigator.startNode.route) {
        Node.Top.composable(this) {
            TopAnimeScreen()
        }
        Node.Details.composable(this) {
            argument(dummy, it.arguments).let { Timber.e("dummy is: $it") }
            AnimeDetailsScreen()
        }
    }
}

internal sealed class Node(name: String) : NavigationNode(name) {
    object Top : Node("top")

    object Details : Node("details") {
        val dummy = typedArgument("dummy", JsonNavType<Dummy>(false))

        override val arguments = listOf(dummy)
    }
}

data class Dummy(val name: String)