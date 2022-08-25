package com.doskoch.template.core.components.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable

data class TypedArgument<T>(val namedNavArgument: NamedNavArgument, val navType: NavType<T>)

abstract class NavigationNode {
    abstract val params: NodeParams

    fun <T> argument(arg: TypedArgument<T>, bundle: Bundle?): T = arg.navType[bundle!!, arg.namedNavArgument.name]!!

    fun <T> route(vararg arguments: Pair<TypedArgument<T>, T>) = buildString {
        append(params.nodeName)

        params.arguments.forEach { (argumentName) ->
            arguments
                .find { (typedArgument) -> typedArgument.namedNavArgument.name == argumentName }
                ?.let { (_, value) -> append("/$value") }
        }
    }
}

@Suppress("MemberVisibilityCanBePrivate")
class NodeParams(
    val nodeName: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList()
) {
    val path: String
    val arguments: Map<String, TypedArgument<*>>
    val deepLinks: List<NavDeepLink>

    init {
        this.path = buildString {
            append(nodeName)

            val hasDefault = arguments.groupBy { it.argument.isNullable || it.argument.isDefaultValuePresent }

            hasDefault[false]?.forEach { argument -> append("/{", argument.name, "}") }

            hasDefault[true]
                ?.joinToString(prefix = "?", separator = "&") { argument -> "${argument.name}={${argument.name}}" }
                ?.let { append(it) }
        }

        this.arguments = arguments.associate { namedNavArgument ->
            namedNavArgument.name to TypedArgument(namedNavArgument, namedNavArgument.argument.type)
        }
        this.deepLinks = deepLinks
    }
}

val <N : NavigationNode> N.composable: NavGraphBuilder.(@Composable N.(NavBackStackEntry) -> Unit) -> Unit
    get() = { content ->
        val node = this@composable

        composable(
            route = node.params.path,
            arguments = node.params.arguments.values.map { it.namedNavArgument },
            deepLinks = node.params.deepLinks,
            content = { navBackStackEntry -> content(node, navBackStackEntry) }
        )
    }