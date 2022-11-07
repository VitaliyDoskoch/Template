package com.doskoch.template.core.di

import com.doskoch.template.core.store.AuthDataStore

interface CoreComponent {
    val authDataStore: AuthDataStore
}
