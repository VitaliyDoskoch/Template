package com.doskoch.template.navigation

import androidx.compose.runtime.Composable
import com.doskoch.template.anime.navigation.AnimeFeatureNavGraph
import com.doskoch.template.auth.navigation.AuthFeatureNavGraph
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
        Node.Auth.composable(this) {
            AuthFeatureNavGraph()
        }
        Node.Anime.composable(this) {
            AnimeFeatureNavGraph()
        }
    }
}

internal sealed class Node(name: String) : NavigationNode(name) {
    object Splash : Node("splash")
    object Auth : Node("auth")
    object Anime : Node("anime")
}
