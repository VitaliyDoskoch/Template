package com.doskoch.template.di.modules

import com.doskoch.template.TIMBER_HTTP_LOG_LEVEL
import com.doskoch.template.api.jikan.di.external.ExternalLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import timber.log.Timber

@Module
@InstallIn(SingletonComponent::class)
interface JikanApiModule {
    companion object {
        @Provides
        fun logger() = ExternalLogger { Timber.log(TIMBER_HTTP_LOG_LEVEL, it) }
    }
}