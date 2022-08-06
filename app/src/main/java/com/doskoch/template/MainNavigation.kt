package com.doskoch.template

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.doskoch.template.features.splash.SplashScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Destinations.Splash.name) {
        composable(Destinations.Splash.name) {
            SplashScreen()
        }
    }
}

private enum class Destinations {
    Splash
}