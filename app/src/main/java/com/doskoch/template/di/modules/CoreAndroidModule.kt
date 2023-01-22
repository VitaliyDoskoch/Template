package com.doskoch.template.di.modules

import com.doskoch.template.core.android.components.error.ErrorResponseParser
import com.doskoch.template.error.ErrorResponseParserImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CoreAndroidModule {
    @Binds
    fun errorResponseParser(impl: ErrorResponseParserImpl): ErrorResponseParser
}