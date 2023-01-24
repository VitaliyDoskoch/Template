package com.doskoch.template.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.doskoch.template.anime.presentation.navigation.AnimeFeatureNavGraph
import com.doskoch.template.auth.presentation.navigation.AuthFeatureNavGraph
import com.doskoch.template.core.android.components.navigation.CoreNavGraph
import com.doskoch.template.core.android.components.navigation.NavigationNode
import com.doskoch.template.core.android.components.navigation.composable
import com.doskoch.template.di.modules.AppModule
import com.doskoch.template.splash.presentation.screens.splash.SplashScreen
import dagger.hilt.android.EntryPointAccessors

@Composable
fun MainNavGraph() {
    val appContext = LocalContext.current.applicationContext
    val navigator = remember { EntryPointAccessors.fromApplication(appContext, AppModule.EntryPoint::class.java).navigator() }

    CoreNavGraph(navigator = navigator) {
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
