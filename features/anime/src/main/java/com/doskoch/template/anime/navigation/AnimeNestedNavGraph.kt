package com.doskoch.template.anime.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.doskoch.template.anime.di.Injector
import com.doskoch.template.anime.screens.details.AnimeDetailsScreen
import com.doskoch.template.anime.screens.top.TopAnimeScreen
import com.doskoch.template.core.components.navigation.JsonNavType
import com.doskoch.template.core.components.navigation.NavigationNode
import com.doskoch.template.core.components.navigation.composable
import com.doskoch.template.core.components.navigation.typedArgument
import timber.log.Timber

@Composable
fun AnimeNestedNavGraph() {
    val navController = rememberNavController()

    LaunchedEffect(navController) {
        Injector.nestedNavigator.events.collect { it.invoke(navController) }
    }

    NavHost(navController = navController, startDestination = AnimeNestedNavigator.startNode.route) {
        Node.Top.composable(this) {
            TopAnimeScreen()
        }
        Node.Details.composable(this) {
            requiredBool.extractFrom(it.arguments).let { Timber.e("requiredBool: $it") }
            optionalInt.extractFrom(it.arguments).let { Timber.e("optionalInt: $it") }
            optionalJson.extractFrom(it.arguments).let { Timber.e("optionalJson: $it") }
            requiredJson.extractFrom(it.arguments).let { Timber.e("requiredJson: $it") }
            optionalString.extractFrom(it.arguments).let { Timber.e("optionalString: $it") }
            AnimeDetailsScreen()
        }
    }
}

internal sealed class Node(name: String) : NavigationNode(name) {
    object Top : Node("top")

    object Details : Node("details") {
        val requiredBool = typedArgument("requiredBool", NavType.BoolType)
        val optionalInt = typedArgument("optionalInt", NavType.IntType) { defaultValue = 0 }
        val optionalJson = typedArgument("optionalJson", JsonNavType<Lol>(true)) { nullable = true; defaultValue = null }
        val requiredJson = typedArgument("requiredJson", JsonNavType<Dummy>(false))
        val optionalString = typedArgument("optionalString", NavType.StringType) { defaultValue = "optionalString" }

        override val arguments = listOf(requiredBool, optionalInt, optionalJson, requiredJson, optionalString)
    }
}

data class Lol(val name: String)
data class Dummy(val name: String)