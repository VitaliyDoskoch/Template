package com.doskoch.template.authorization.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.doskoch.template.authorization.di.Injector
import com.doskoch.template.authorization.screens.signIn.SignInScreen
import com.doskoch.template.authorization.screens.signUp.SignUpScreen

@Composable
fun AuthorizationNestedNavGraph() {
    val navController = rememberNavController()

    LaunchedEffect(navController) {
        Injector.nestedNavigator.events.collect { it.invoke(navController) }
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