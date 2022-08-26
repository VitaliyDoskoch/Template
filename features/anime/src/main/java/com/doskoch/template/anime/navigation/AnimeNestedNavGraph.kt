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
import com.doskoch.template.core.components.navigation.NodeParams
import com.doskoch.template.core.components.navigation.TypedArgument
import com.doskoch.template.core.components.navigation.composable
import com.doskoch.template.core.components.navigation.typedArgument
import timber.log.Timber

@Composable
fun AnimeNestedNavGraph() {
    val navController = rememberNavController()

    LaunchedEffect(navController) {
        Injector.nestedNavigator.events.collect { it.invoke(navController) }
    }

    NavHost(navController = navController, startDestination = AnimeNestedNavigator.startNode.params.path) {
        Node.Top.composable(this) {
            TopAnimeScreen()
        }
        Node.Details.composable(this) {
            argument(first, it.arguments).let { Timber.e("first=$it") }
            AnimeDetailsScreen()
        }
    }
}

internal sealed class Node : NavigationNode() {
    object Top : Node() {
        override val params = NodeParams("top")
    }

    object Details : Node() {
        override val params = NodeParams(
            nodeName = "details",
            arguments = listOf(
                typedArgument("first", JsonNavType<Dummy>(false))
            )
        )

        val first: TypedArgument<Dummy> by params.arguments
    }
}

data class Dummy(val name: String)