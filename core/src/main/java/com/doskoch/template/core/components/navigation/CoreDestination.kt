package com.doskoch.template.core.components.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable

open class CoreDestination(
    private val name: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList()
){
    val route: String
    val arguments: Map<String, NamedNavArgument>
    val deepLinks: List<NavDeepLink>

    init {
        this.route = buildString {
            append(name)

            val hasDefault = arguments.groupBy { it.argument.isNullable || it.argument.isDefaultValuePresent }

            hasDefault[false]?.forEach { argument -> append("/{", argument.name, "}") }

            hasDefault[true]
                ?.joinToString(prefix = "?", separator = "&") { argument -> "${argument.name}={${argument.name}}" }
                ?.let { append(it) }
        }

        this.arguments = arguments.associateBy { it.name }
        this.deepLinks = deepLinks
    }

    fun route(arguments: Map<String, Any>) = buildString {
        append(name)
        this@CoreDestination.arguments.forEach { (key) ->
            arguments[key]?.let { append("/$it") }
        }
    }
}

val <D : CoreDestination> D.composable: NavGraphBuilder.(@Composable D.(NavBackStackEntry) -> Unit) -> Unit
    get() = { content ->
        val destination = this@composable

        composable(
            route = destination.route,
            arguments = destination.arguments.values.toList(),
            deepLinks = destination.deepLinks,
            content = { navBackStackEntry -> content(destination, navBackStackEntry) }
        )
    }