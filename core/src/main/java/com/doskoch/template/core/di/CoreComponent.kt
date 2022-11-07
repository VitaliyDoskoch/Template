package com.doskoch.template.core.di

import com.doskoch.template.core.store.AuthorizationDataStore

interface CoreComponent {
    val authorizationDataStore: AuthorizationDataStore
}