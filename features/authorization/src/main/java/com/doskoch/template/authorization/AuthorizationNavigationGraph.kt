package com.doskoch.template.authorization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.doskoch.template.authorization.signIn.SignInScreen
import com.doskoch.template.authorization.signUp.SignUpScreen

@Composable
fun AuthorizationNavigationGraph() {
    val navController = rememberNavController()

    LaunchedEffect(navController) {
        Injector.nestedNavigator.navController = navController
    }

    NavHost(navController = navController, startDestination = AuthorizationNestedNavigator.startDestination.name) {
        composable(Destinations.SignUp.name) {
            SignUpScreen()
        }
        composable(Destinations.SignIn.name) {
            SignInScreen()
        }
    }
}

internal enum class Destinations {
    SignUp,
    SignIn
}