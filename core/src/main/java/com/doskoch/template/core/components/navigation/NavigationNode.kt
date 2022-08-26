package com.doskoch.template.core.components.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.gson.Gson
import timber.log.Timber

abstract class NavigationNode {
    abstract val params: NodeParams

    fun <T> argument(typedArgument: TypedArgument<T>, bundle: Bundle?): T {
        return typedArgument.type.get(bundle!!, typedArgument.value.name)!!
    }

    fun route(builder: NavigationNode.RouteBuilder.() -> Unit = {}): String {
        return RouteBuilder()
            .apply(builder)
            .build()
    }

    inner class RouteBuilder internal constructor() {
        private val arguments = mutableMapOf<TypedArgument<*>, Any>()

        fun <T : Any> setArgument(argument: Pair<TypedArgument<T>, T>): NavigationNode.RouteBuilder {
            arguments[argument.first] = argument.second
            return this@RouteBuilder
        }

        internal fun build() = buildString {
            append(params.nodeName)

            val hasDefault = params.arguments.values.groupBy { it.value.argument.isNullable || it.value.argument.isDefaultValuePresent }

            hasDefault[false]?.forEach { argument ->
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
            route = node.params.path,
            arguments = node.params.arguments.values.map { it.value },
            deepLinks = node.params.deepLinks,
            content = { navBackStackEntry -> content(node, navBackStackEntry) }
        )
    }