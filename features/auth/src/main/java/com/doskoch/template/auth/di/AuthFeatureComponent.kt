package com.doskoch.template.auth.di

import com.doskoch.template.auth.navigation.AuthFeatureNavigator
import com.doskoch.template.core.components.kotlin.DestroyableLazy
import dagger.hilt.DefineComponent
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
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

    @EntryPoint
    @InstallIn(AuthFeatureComponent::class)
    interface ComponentEntryPoint {
        fun navigator(): AuthFeatureNavigator
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface BuilderEntryPoint {
        fun provideBuilder(): Builder
    }
}

var authFeatureComponentHolder: DestroyableLazy<AuthFeatureComponent>? = null