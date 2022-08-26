package com.doskoch.template.core.components.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument

data class TypedArgument<T>(val value: NamedNavArgument, val type: NavType<T>)

fun <T> typedArgument(name: String, type: NavType<T>, builder: NavArgumentBuilder.() -> Unit = {}) = TypedArgument(
    value = navArgument(name) {
        builder.invoke(this)
        this.type = type
    },
    type = type
)