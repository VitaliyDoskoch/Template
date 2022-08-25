package com.doskoch.template.core.components.navigation

import androidx.navigation.NavDeepLink

@Suppress("MemberVisibilityCanBePrivate")
class NodeParams(
    val nodeName: String,
    arguments: List<TypedArgument<*>> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList()
) {
    val path: String
    val arguments: Map<String, TypedArgument<*>>
    val deepLinks: List<NavDeepLink>

    init {
        this.path = buildString {
            append(nodeName)

            val hasDefault = arguments.groupBy { it.value.argument.isNullable || it.value.argument.isDefaultValuePresent }

            hasDefault[false]?.forEach { argument -> append("/{", argument.value.name, "}") }

            hasDefault[true]
                ?.joinToString(prefix = "?", separator = "&") { argument -> "${argument.value.name}={${argument.value.name}}" }
                ?.let { append(it) }
        }

        this.arguments = arguments.associateBy { it.value.name }
        this.deepLinks = deepLinks
    }
}