package com.doskoch.template.anime.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.doskoch.template.anime.di.Injector
import com.doskoch.template.anime.screens.top.TopAnimeScreen

@Composable
fun AnimeNestedNavGraph() {
    val navController = rememberNavController()

    LaunchedEffect(navController) {
        Injector.nestedNavigator.events.collect { it.invoke(navController) }
    }

    NavHost(navController = navController, startDestination = AnimeNestedNavigator.startDestination.name) {
        composable(Destinations.Top.name) {
            TopAnimeScreen()
        }
        composable(Destinations.Favorite.name) {

        }
        composable(Destinations.Details.name) {

        }
    }
}

internal enum class Destinations {
    Top,
    Favorite,
    Details
}