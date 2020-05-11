package com.extensions.android.components.preferences

import android.content.Context
import android.content.SharedPreferences

/**
 * It is a wrapper on [SharedPreferences], that contains helper methods.
 * @param [fileName] preferences' file name. See [Context.getSharedPreferences] for further info.
 * @param [mode] access mode. See [Context.getSharedPreferences] for further info.
 */
abstract class BasePreferences(
    protected val context: Context,
    protected val fileName: String,
    protected val mode: Int
) {

    val preferences: SharedPreferences
        get() = context.getSharedPreferences(fileName, mode)

    fun contains(key: String) = preferences.contains(key)

    fun all(): MutableMap<String, *> = preferences.all

    fun getString(key: String, defaultValue: String? = null) =
        preferences.getString(key, defaultValue)

    fun getStringSet(key: String, defaultValue: Set<String> = setOf()) =
        preferences.getStringSet(key, defaultValue)

    fun getBoolean(key: String, defaultValue: Boolean = false) =
        preferences.getBoolean(key, defaultValue)

    fun getInt(key: String, defaultValue: Int = 0) = preferences.getInt(key, defaultValue)

    fun getLong(key: String, defaultValue: Long = 0L) = preferences.getLong(key, defaultValue)

    fun getFloat(key: String, defaultValue: Float = 0f) =
        preferences.getFloat(key, defaultValue)

    fun putString(key: String, value: String?) {
        preferences
            .edit()
            .putString(key, value)
            .apply()
    }

    fun putStringSet(key: String, value: Set<String?>) {
        preferences
            .edit()
            .putStringSet(key, value)
            .apply()
    }

    fun putBoolean(key: String, value: Boolean) {
        preferences
            .edit()
            .putBoolean(key, value)
            .apply()
    }

    fun putInt(key: String, value: Int) {
        preferences
            .edit()
            .putInt(key, value)
            .apply()
    }

    fun putLong(key: String, value: Long) {
        preferences
            .edit()
            .putLong(key, value)
            .apply()
    }

    fun putFloat(key: String, value: Float) {
        preferences
            .edit()
            .putFloat(key, value)
            .apply()
    }

    fun remove(key: String) {
        preferences
            .edit()
            .remove(key)
            .apply()
    }

    open fun clear() {
        preferences
            .edit()
            .clear()
            .apply()
    }
}