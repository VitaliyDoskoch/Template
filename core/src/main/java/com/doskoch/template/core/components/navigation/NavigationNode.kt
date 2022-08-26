package com.doskoch.template.core.components.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.gson.Gson
import timber.log.Timber

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

    fun <T> TypedArgument<T>.extractFrom(bundle: Bundle?): T? = type[bundle!!, value.name]

    fun route(builder: NavigationNode.RouteBuilder.() -> Unit = {}): String = RouteBuilder().apply(builder).build()

    inner class RouteBuilder internal constructor() {
        private val args = mutableMapOf<TypedArgument<*>, Any?>()

        infix fun <T : Any?> TypedArgument<T>.setTo(value: T) {
            args[this] = value
        }

        internal fun build() = buildString {
            append(name)

            val byHasDefault = arguments.groupBy { it.value.argument.isNullable || it.value.argument.isDefaultValuePresent }

            byHasDefault[false]?.forEach { argument ->
                args[argument]
                    ?.let { if(argument.type is JsonNavType) Gson().toJson(it, it::class.java) else it }
                    ?.let { append("/$it") }
                    ?: throw NoSuchElementException("The required argument is missing")
            }

            byHasDefault[true]
                ?.joinToString(prefix = "?", separator = "&") { argument ->
                    val value = args[argument]
                        ?.let { if(argument.type is JsonNavType) Gson().toJson(it, it::class.java) else it }
                    "${argument.value.name}=${value ?: argument.value.argument.defaultValue}"
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