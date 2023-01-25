package com.doskoch.template.core.android.di

import com.doskoch.template.core.android.ext.entryPoint
import com.doskoch.template.core.kotlin.di.ComponentAccessor
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CoreAndroidInjector {

    companion object {
        private fun entryPoint() = CoreAndroidComponentAccessor.entryPoint<CoreAndroidComponent.EntryPoint>()

        fun errorMapper() = entryPoint().errorMapper()
    }
}

object CoreAndroidComponentAccessor : ComponentAccessor<CoreAndroidComponent>()
