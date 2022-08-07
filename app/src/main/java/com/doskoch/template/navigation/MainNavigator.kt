package com.doskoch.template.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

class MainNavigator {

    lateinit var navController: NavController

    fun navigateUp() = navController.navigateUp()

    fun toAuthorization(options: NavOptions?) = navController.navigate(Destinations.Authorization.name, options)

    companion object {
        val startDestination = Destinations.Splash
    }
}