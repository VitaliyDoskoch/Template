package com.doskoch.template.anime.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.doskoch.template.anime.di.Injector
import com.doskoch.template.anime.screens.details.AnimeDetailsScreen
import com.doskoch.template.anime.screens.favorite.FavoriteAnimeScreen
import com.doskoch.template.anime.screens.top.TopAnimeScreen
import com.doskoch.template.core.components.navigation.JsonNavType
import com.doskoch.template.core.components.navigation.NavigationNode
import com.doskoch.template.core.components.navigation.NotNullStringType
import com.doskoch.template.core.components.navigation.composable
import com.doskoch.template.core.components.navigation.typedArgument
import timber.log.Timber

@Composable
fun AnimeFeatureNavGraph() {
    val navController = rememberNavController()

    LaunchedEffect(navController) {
        Injector.navigator.events.collect { it.invoke(navController) }
    }

    NavHost(navController = navController, startDestination = AnimeFeatureNavigator.startNode.route) {
        Node.Top.composable(this) {
            TopAnimeScreen()
        }
        Node.Favorite.composable(this) {
            FavoriteAnimeScreen()
        }
        Node.Details.composable(this) {
            AnimeDetailsScreen()
        }
    }
}

internal sealed class Node(name: String) : NavigationNode(name) {
    object Top : Node("top")
    object Favorite : Node("favorite")

    object Details : Node("details") {
        val animeId = typedArgument("animeId", NavType.IntType)

        fun buildRoute(animeId: Int) = buildRoute(this.animeId setValue animeId)
    }
}