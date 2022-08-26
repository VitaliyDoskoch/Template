package com.doskoch.template.core.components.navigation

import androidx.navigation.NavDeepLink

class NodeParams(
    val name: String,
    val arguments: List<TypedArgument<*>> = emptyList(),
    val deepLinks: List<NavDeepLink> = emptyList()
) {
    val path: String = buildString {
        append(name)

        val hasDefault = arguments.groupBy { it.value.argument.isNullable || it.value.argument.isDefaultValuePresent }

        hasDefault[false]?.forEach { argument -> append("/{", argument.value.name, "}") }

        hasDefault[true]
            ?.joinToString(prefix = "?", separator = "&") { argument -> "${argument.value.name}={${argument.value.name}}" }
            ?.let { append(it) }
    }
}