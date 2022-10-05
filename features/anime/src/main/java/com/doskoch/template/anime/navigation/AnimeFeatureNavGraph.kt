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
import com.doskoch.template.core.components.navigation.NotNullStringType
import com.doskoch.template.core.components.navigation.composable
import com.doskoch.template.core.components.navigation.typedArgument
import timber.log.Timber

@Composable
fun AnimeFeatureNavGraph() {
    val navController = rememberNavController()

    LaunchedEffect(navController) {
        Injector.navigator.events.collect { it.invoke(navController) }
    }

    NavHost(navController = navController, startDestination = AnimeFeatureNavigator.startNode.route) {
        Node.Top.composable(this) {
            TopAnimeScreen()
        }
        Node.Details.composable(this) {
            requiredBool.valueFrom(it.arguments!!).let { Timber.e("requiredBool: $it") }
            optionalInt.valueFrom(it.arguments!!).let { Timber.e("optionalInt: $it") }
            optionalNullableJson.valueFrom(it.arguments!!).let { Timber.e("optionalNullableJson: $it") }
            requiredJson.valueFrom(it.arguments!!).let { Timber.e("requiredJson: $it") }
            optionalString.valueFrom(it.arguments!!).let { Timber.e("optionalString: $it") }
            optionalNonNullableJson.valueFrom(it.arguments!!).let { Timber.e("optionalNonNullableJson: $it") }
            AnimeDetailsScreen()
        }
    }
}

internal sealed class Node(name: String) : NavigationNode(name) {
    object Top : Node("top")

    object Details : Node("details") {
        val requiredBool = typedArgument("requiredBool", NavType.BoolType)
        val optionalInt = typedArgument("optionalInt", NavType.IntType, defaultValue = 0)
        val optionalNullableJson = typedArgument("optionalNullableJson", JsonNavType<Lol?>(), defaultValue = null)
        val requiredJson = typedArgument("requiredJson", JsonNavType<Dummy>())
        val optionalString = typedArgument("optionalString", NavType.NotNullStringType, defaultValue = "optionalString")
        val optionalNonNullableJson = typedArgument("optionalNonNullableJson", JsonNavType<Lol>(), defaultValue = Lol("lol"))

        fun buildRoute(
            rBool: Boolean,
            oInt: Int = optionalInt.defaultValue,
            oNullableJson: Lol? = optionalNullableJson.defaultValue,
            rJson: Dummy,
            oString: String = optionalString.defaultValue.orEmpty(),
            oNonNullableJson: Lol = optionalNonNullableJson.defaultValue
        ) = buildRoute(
            requiredBool setValue rBool,
            optionalInt setValue oInt,
            optionalNullableJson setValue oNullableJson,
            requiredJson setValue rJson,
            optionalString setValue oString,
            optionalNonNullableJson setValue oNonNullableJson
        )
    }
}

data class Lol(val name: String)
data class Dummy(val name: String)