package com.doskoch.template.core.components.navigation

import android.os.Bundle
import androidx.navigation.NavType

val NavType.Companion.NotNullStringType: NavType<String>
    get() = NotNullStringTypeImpl

private object NotNullStringTypeImpl : NavType<String>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): String? = bundle.getString(key)

    override fun parseValue(value: String): String = value

    override fun put(bundle: Bundle, key: String, value: String) = bundle.putString(key, value)
}
