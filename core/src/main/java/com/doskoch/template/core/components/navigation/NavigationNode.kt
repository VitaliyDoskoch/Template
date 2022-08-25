package com.doskoch.template.core.components.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

abstract class NavigationNode {
    abstract val params: NodeParams

    fun <T> argument(typedArgument: TypedArgument<T>, bundle: Bundle?): T = typedArgument.type[bundle!!, typedArgument.value.name]!!

    fun <T> route(vararg arguments: Pair<TypedArgument<T>, T>) = buildString {
        append(params.nodeName)

        params.arguments.forEach { (argumentName) ->
            arguments
                .find { (typedArgument) -> typedArgument.value.name == argumentName }
                ?.let { (_, value) -> append("/$value") }
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