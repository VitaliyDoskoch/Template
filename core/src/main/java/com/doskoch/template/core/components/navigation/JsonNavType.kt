package com.doskoch.template.core.components.navigation

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlin.reflect.KClass

class JsonNavType<T : Any>(private val kClass: KClass<T>, nullable: Boolean) : NavType<T>(nullable) {

    override fun get(bundle: Bundle, key: String): T? = bundle.getString(key)?.let { Gson().fromJson(it, kClass.java) }

    override fun parseValue(value: String): T = Gson().fromJson(value, kClass.java)

    override fun put(bundle: Bundle, key: String, value: T) = bundle.putString(key, Gson().toJson(value))
}

@Suppress("FunctionName")
inline fun <reified T : Any> JsonNavType(nullable: Boolean) = JsonNavType(T::class, nullable)