package com.doskoch.template.core.data.store

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class AuthorizationDataStore(private val context: Context) {

    private val Context.store by preferencesDataStore("AuthorizationDataStore")

    fun authorized() = context.store.data.map { it[AUTHORIZED] }

    suspend fun updateAuthorized(value: Boolean) = context.store.edit { it[AUTHORIZED] = value }

    companion object {
        private val AUTHORIZED = booleanPreferencesKey("AUTHORIZED")
    }
}