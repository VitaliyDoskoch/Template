package com.doskoch.template.core.components.navigation

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson

@Suppress("FunctionName")
inline fun <reified T : Any> JsonNavType(nullable: Boolean) = object : NavType<T>(nullable) {
    override fun get(bundle: Bundle, key: String): T? = bundle.getString(key)?.let { Gson().fromJson(it, T::class.java) }

    override fun parseValue(value: String): T = Gson().fromJson(value, T::class.java)

    override fun put(bundle: Bundle, key: String, value: T) = bundle.putString(key, Gson().toJson(value))
}