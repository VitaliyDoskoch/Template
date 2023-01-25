package com.doskoch.template.core.android.di

import android.content.Context
import com.doskoch.template.core.android.components.error.ErrorMapper
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface CoreAndroidEntryPoint {
    fun errorMapper(): ErrorMapper
}

fun Context.errorMapper() = EntryPointAccessors
    .fromApplication(applicationContext, CoreAndroidEntryPoint::class.java)
    .errorMapper()
