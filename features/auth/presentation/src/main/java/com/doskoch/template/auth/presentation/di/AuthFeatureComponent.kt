package com.doskoch.template.auth.presentation.di

import com.doskoch.template.auth.presentation.navigation.AuthFeatureNavigator
import dagger.hilt.DefineComponent
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Scope

@Scope
@MustBeDocumented
@Retention(value = AnnotationRetention.RUNTIME)
annotation class AuthFeatureScope

@AuthFeatureScope
@DefineComponent(parent = SingletonComponent::class)
interface AuthFeatureComponent {

    @DefineComponent.Builder
    interface Builder {
        fun build(): AuthFeatureComponent
    }

    @dagger.hilt.EntryPoint
    @InstallIn(AuthFeatureComponent::class)
    interface EntryPoint {
        fun authFeatureNavigator(): AuthFeatureNavigator
    }
}
