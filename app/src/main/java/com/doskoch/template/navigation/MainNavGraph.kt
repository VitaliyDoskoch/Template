package com.doskoch.template.navigation

import androidx.compose.runtime.Composable
import com.doskoch.template.anime.navigation.AnimeFeatureNavGraph
import com.doskoch.template.authorization.navigation.AuthorizationFeatureNavGraph
import com.doskoch.template.core.components.navigation.CoreNavGraph
import com.doskoch.template.core.components.navigation.NavigationNode
import com.doskoch.template.core.components.navigation.composable
import com.doskoch.template.di.AppInjector
import com.doskoch.template.splash.screens.splash.SplashScreen

@Composable
fun MainNavGraph() {
    CoreNavGraph(navigator = AppInjector.component.navigator) {
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
