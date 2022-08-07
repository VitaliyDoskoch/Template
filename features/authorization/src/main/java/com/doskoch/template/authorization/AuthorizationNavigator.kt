package com.doskoch.template.authorization

import androidx.navigation.NavController
import androidx.navigation.NavOptions

class AuthorizationNavigator {

    lateinit var navController: NavController

    fun navigateUp() = navController.navigateUp()

    fun toSignIn() = navController.navigate(Destinations.SignIn.name)

    companion object {
        internal val startDestination = Destinations.SignUp
    }
}