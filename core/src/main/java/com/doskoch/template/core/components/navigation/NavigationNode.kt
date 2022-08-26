package com.doskoch.template.core.components.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.gson.Gson

abstract class NavigationNode(private val name: String) {
    open val arguments: List<TypedArgument<*>> = emptyList()
    open val deepLinks: List<NavDeepLink> = emptyList()

    val route: String
        get() = buildString {
            append(name)

            val byHasDefault = arguments.groupBy { it.value.argument.isNullable || it.value.argument.isDefaultValuePresent }

            byHasDefault[false]?.forEach { argument -> append("/{", argument.value.name, "}") }

            byHasDefault[true]
                ?.joinToString(prefix = "?", separator = "&") { argument -> "${argument.value.name}={${argument.value.name}}" }
                ?.let { append(it) }
        }

    fun route(builder: NavigationNode.RouteBuilder.() -> Unit = {}): String {
        return RouteBuilder()
            .apply(builder)
            .build()
    }

    fun <T> TypedArgument<T>.extractFrom(bundle: Bundle?): T = type[bundle!!, value.name]!!

    inner class RouteBuilder internal constructor() {
        private val arguments = mutableMapOf<TypedArgument<*>, Any>()

        fun <T : Any> setArgument(argument: Pair<TypedArgument<T>, T>): NavigationNode.RouteBuilder {
            arguments[argument.first] = argument.second
            return this@RouteBuilder
        }

        internal fun build() = buildString {
            append(name)

            val hasDefault = this@NavigationNode.arguments.groupBy { it.value.argument.isNullable || it.value.argument.isDefaultValuePresent }

            hasDefault[false]?.forEach { argument ->
                //TODO: manage Gson
                arguments[argument]
                    ?.let { value -> append("/${Gson().toJson(value, value::class.java)}") } ?: throw NoSuchElementException("The required argument is missing")
            }

            hasDefault[true]
                ?.joinToString(prefix = "?", separator = "&") { argument ->
                    "${argument.value.name}=${arguments[argument] ?: argument.value}"
                }
                ?.let { append(it) }
        }
    }
}

val <N : NavigationNode> N.composable: NavGraphBuilder.(@Composable N.(NavBackStackEntry) -> Unit) -> Unit
    get() = { content ->
        val node = this@composable

        composable(
            route = node.route,
            arguments = node.arguments.map { it.value },
            deepLinks = node.deepLinks,
            content = { navBackStackEntry -> content(node, navBackStackEntry) }
        )
    }