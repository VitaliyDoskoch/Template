package com.doskoch.template.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.doskoch.template.authorization.AuthorizationNavigationGraph
import com.doskoch.template.authorization.signUp.SignUpScreen
import com.doskoch.template.di.AppInjector
import com.doskoch.template.features.splash.SplashScreen

@Composable
fun MainNavigationGraph() {
    val navController = rememberNavController()

    LaunchedEffect(navController) {
        AppInjector.component.navigator.navController = navController
    }

    NavHost(navController = navController, startDestination = MainNavigator.startDestination.name) {
        composable(Destinations.Splash.name) {
            SplashScreen()
        }
        composable(Destinations.Authorization.name) {
            AuthorizationNavigationGraph()
        }
        composable(Destinations.Main.name) {

        }
    }
}

enum class Destinations {
    Splash,
    Authorization,
    Main
}