package com.doskoch.template.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.doskoch.template.anime.navigation.AnimeNestedNavGraph
import com.doskoch.template.authorization.navigation.AuthorizationNestedNavGraph
import com.doskoch.template.di.AppInjector
import com.doskoch.template.splash.SplashScreen

@Composable
fun MainNavGraph() {
    val navController = rememberNavController()

    LaunchedEffect(navController) {
        AppInjector.component.mainNavigator.events.collect { it.invoke(navController) }
    }

    NavHost(navController = navController, startDestination = MainNavigator.startDestination.name) {
        composable(Destinations.Splash.name) {
            SplashScreen()
        }
        composable(Destinations.Authorization.name) {
            AuthorizationNestedNavGraph()
        }
        composable(Destinations.Anime.name) {
            AnimeNestedNavGraph()
        }
    }
}

enum class Destinations {
    Splash,
    Authorization,
    Anime
}