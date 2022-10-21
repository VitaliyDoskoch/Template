package com.doskoch.template.core.components.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.gson.Gson

abstract class NavigationNode(private val name: String) {
    open val arguments: List<TypedArgument<*>> by lazy(::retrieveArguments)
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

    protected fun buildRoute(vararg parameters: NavParameter<*>) = buildString {
        val params = parameters.associateBy { it.typedArgument }

        append(name)

        val byHasDefault = arguments.groupBy { it.value.argument.isNullable || it.value.argument.isDefaultValuePresent }

        byHasDefault[false]?.forEach { argument ->
            params[argument]
                ?.value
                ?.let { if (argument.type is JsonNavType) Gson().toJson(it, it::class.java) else it }
                ?.let { append("/$it") }
                ?: throw NoSuchElementException("The required argument is missing")
        }

        byHasDefault[true]
            ?.joinToString(prefix = "?", separator = "&") { argument ->
                val value = params[argument]?.value
                    ?.let { if (argument.type is JsonNavType) Gson().toJson(it, it::class.java) else it }
                val defaultValue = argument.defaultValue
                    ?.let { if (argument.type is JsonNavType) Gson().toJson(it, it::class.java) else it }

                "${argument.value.name}=${value ?: defaultValue}"
            }
            ?.let { append(it) }
    }

    private fun retrieveArguments() = javaClass.declaredFields
        .filter { it.type.isAssignableFrom(TypedArgument::class.java) }
        .onEach { it.isAccessible = true }
        .map { it.get(this) as TypedArgument<*> }

    protected infix fun <T> TypedArgument<T>.setValue(value: T) = NavParameter(this, value)

    data class NavParameter<T>(val typedArgument: TypedArgument<T>, val value: T)
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
