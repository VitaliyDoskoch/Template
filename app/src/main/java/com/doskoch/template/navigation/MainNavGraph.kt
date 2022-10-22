package com.doskoch.template.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.doskoch.template.anime.navigation.AnimeFeatureNavGraph
import com.doskoch.template.authorization.navigation.AuthorizationFeatureNavGraph
import com.doskoch.template.core.components.navigation.NavigationNode
import com.doskoch.template.core.components.navigation.composable
import com.doskoch.template.di.AppInjector
import com.doskoch.template.splash.screens.splash.SplashScreen

@Composable
fun MainNavGraph() {
    val navController = rememberNavController()

    LaunchedEffect(navController) {
        AppInjector.component.navigator.events.collect { it.invoke(navController) }
    }

    NavHost(navController = navController, startDestination = MainNavigator.startDestination.route) {
        Node.Splash.composable(this) {
            SplashScreen()
        }
        Node.Authorization.composable(this) {
            AuthorizationFeatureNavGraph()
        }
        Node.Anime.composable(this) {
            AnimeFeatureNavGraph()
        }
    }
}

internal sealed class Node(name: String) : NavigationNode(name) {
    object Splash : Node("splash")
    object Authorization : Node("authorization")
    object Anime : Node("anime")
}
