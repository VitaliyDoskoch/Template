package com.doskoch.legacy.android.functions

import java.net.HttpURLConnection
import java.net.URL

/**
 * Performs check whether we could access the server by passed url and whether we have any chance
 * to get a valid response.
 * @param [url] api url.
 * @param [connectTimeout] connection timeout.
 * @param [readTimeout] read timeout.
 */
fun isAccessible(url: String, connectTimeout: Int, readTimeout: Int): Boolean {
    val connection = (URL(url).openConnection() as HttpURLConnection).apply {
        requestMethod = "HEAD"
        setConnectTimeout(connectTimeout)
        setReadTimeout(readTimeout)
    }

    return connection.responseCode in 200..299
}
