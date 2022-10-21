package com.doskoch.template.authorization.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.doskoch.template.authorization.di.Injector
import com.doskoch.template.authorization.screens.signIn.SignInScreen
import com.doskoch.template.authorization.screens.signUp.SignUpScreen
import com.doskoch.template.core.components.navigation.NavigationNode
import com.doskoch.template.core.components.navigation.composable

@Composable
fun AuthorizationFeatureNavGraph() {
    val navController = rememberNavController()

    LaunchedEffect(navController) {
        Injector.navigator.events.collect { it.invoke(navController) }
    }

    NavHost(navController = navController, startDestination = AuthorizationFeatureNavigator.startNode.route) {
        Node.SignUp.composable(this) {
            SignUpScreen()
        }
        Node.SignIn.composable(this) {
            SignInScreen()
        }
    }
}

internal sealed class Node(name: String) : NavigationNode(name) {
    object SignUp : Node("signUp")
    object SignIn : Node("signIn")
}
