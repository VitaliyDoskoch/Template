package com.doskoch.template.core.android.di

import com.doskoch.template.core.android.components.error.ErrorResponseParser
import dagger.hilt.DefineComponent
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@DefineComponent(parent = SingletonComponent::class)
interface CoreAndroidComponent {

    @DefineComponent.Builder
    interface Builder {
        fun build(): CoreAndroidComponent
    }

    @dagger.hilt.EntryPoint
    @InstallIn(CoreAndroidComponent::class)
    interface EntryPoint {
        fun errorResponseParser(): ErrorResponseParser
    }
}