package com.doskoch.template.core.components.navigation

import android.os.Bundle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

data class TypedArgument<T>(val value: NamedNavArgument, val type: NavType<T>) {
    @Suppress("UNCHECKED_CAST")
    val defaultValue: T = value.argument.defaultValue as T

    @Suppress("UNCHECKED_CAST")
    fun valueFrom(bundle: Bundle): T = type[bundle, value.name] as T
}

inline fun <reified T> typedArgument(name: String, type: NavType<T>) = TypedArgument(
    value = navArgument(name) {
        this.type = type
        this.nullable = null is T
    },
    type = type
)

inline fun <reified T> typedArgument(name: String, type: NavType<T>, defaultValue: T) = TypedArgument(
    value = navArgument(name) {
        this.type = type
        this.nullable = null is T
        this.defaultValue = defaultValue
    },
    type = type
)
