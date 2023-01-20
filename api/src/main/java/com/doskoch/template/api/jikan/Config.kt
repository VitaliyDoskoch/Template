package com.doskoch.template.api.jikan

import android.util.Log
import com.doskoch.template.api.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor.Level

val HTTP_LOG_LEVEL = if (BuildConfig.isLoggingEnabled) Level.BODY else Level.NONE

const val CONNECT_TIMEOUT = 15_000L
const val READ_TIMEOUT = 15_000L

const val BASE_URL = "https://api.jikan.moe/"
