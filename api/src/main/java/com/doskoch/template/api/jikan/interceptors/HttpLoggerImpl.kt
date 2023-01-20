package com.doskoch.template.api.jikan.interceptors

import com.doskoch.template.api.jikan.di.external.ExternalLogger
import com.doskoch.template.api.jikan.di.qualifiers.GsonForLogging
import com.google.gson.Gson
import com.google.gson.JsonParser
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

class HttpLoggerImpl @Inject constructor(
    @GsonForLogging private val gson: Gson,
    private val externalLogger: ExternalLogger
) : HttpLoggingInterceptor.Logger {

    override fun log(message: String) {
        val result: String = try {
            gson.toJson(JsonParser.parseString(message))
        } catch (t: Throwable) {
            message
        }

        externalLogger.log(result)
    }
}
