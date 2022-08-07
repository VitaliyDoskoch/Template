package com.doskoch.template.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

class MainNavigator {

    lateinit var navController: NavController

    fun navigateUp() = navController.navigateUp()

    fun toSignUp(options: NavOptions?) = navController.navigate(Destinations.SignUp.name, options)

    companion object {
        val startDestination = Destinations.Splash
    }
}