package com.doskoch.template.core.components.navigation

import android.os.Bundle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import androidx.navigation.navArgument
import kotlin.reflect.KClass

typealias TypedArgument<T> = Pair<NamedNavArgument, KClass<out T>>

abstract class Node {
    abstract val destination: Destination

    fun <T : Any> get(arg: TypedArgument<T>, bundle: Bundle): T {
        return bundle.get(arg.first.name) as T
    }
}

class Destination(
    private val name: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList()
) {
    val path: String = ""
    val arguments: Map<String, TypedArgument<out Any>> = emptyMap()
}

sealed class Nodes : Node() {
    object Node1 : Nodes() {
        override val destination = Destination(
            "node1",
            listOf(navArgument("first") {})
        )

        val first by destination.arguments
    }
}

fun test(bundle: Bundle) {
    Nodes.Node1.run {
        get(first, bundle)
    }
}