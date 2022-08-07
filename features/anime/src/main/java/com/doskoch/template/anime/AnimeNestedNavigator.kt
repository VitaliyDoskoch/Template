package com.doskoch.template.anime

import androidx.navigation.NavController

class AnimeNestedNavigator {

    lateinit var navController: NavController

    fun navigateUp() = navController.navigateUp()

    fun toFavorite() = navController

    companion object {
        internal val startDestination = Destinations.Top
    }
}