package com.doskoch.template.anime.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.doskoch.template.anime.di.Injector
import com.doskoch.template.anime.screens.details.AnimeDetailsScreen
import com.doskoch.template.anime.screens.top.TopAnimeScreen
import com.doskoch.template.core.components.navigation.CoreDestination
import com.doskoch.template.core.components.navigation.composable

@Composable
fun AnimeNestedNavGraph() {
    val navController = rememberNavController()

    LaunchedEffect(navController) {
        Injector.nestedNavigator.events.collect { it.invoke(navController) }
    }

    NavHost(navController = navController, startDestination = AnimeNestedNavigator.startDestination.route) {
        composable(Destination.Top.route) {
            TopAnimeScreen()
        }
        composable(Destination.Favorite.route) {

        }
        Destination.AnimeDetails.composable(this) {
            it.arguments?.getString(animeId.name)!!
            AnimeDetailsScreen()
        }
    }
}

internal sealed class Destination(
    name: String,
    vararg argument: NamedNavArgument
) : CoreDestination(name, argument.toList()) {

    object Top : Destination("top")

    object Favorite : Destination("favorite")

    object AnimeDetails : Destination("details", navArgument("animeId") { type = NavType.StringType }) {
        val animeId by arguments
    }
}