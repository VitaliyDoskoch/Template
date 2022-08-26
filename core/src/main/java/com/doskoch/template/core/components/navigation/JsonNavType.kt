package com.doskoch.template.core.components.navigation

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson

class JsonNavType<T>(private val kClass: Class<T>, nullable: Boolean) : NavType<T>(nullable) {

    override fun get(bundle: Bundle, key: String): T? = bundle.getString(key)?.let { Gson().fromJson(it, kClass) }

    override fun parseValue(value: String): T = Gson().fromJson(value, kClass)

    override fun put(bundle: Bundle, key: String, value: T) = bundle.putString(key, Gson().toJson(value))
}

@Suppress("FunctionName")
inline fun <reified T> JsonNavType() = JsonNavType(T::class.java, null is T)