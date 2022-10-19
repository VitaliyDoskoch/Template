package com.doskoch.legacy.android.functions

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import java.net.HttpURLConnection
import java.net.URL

/**
 * Check if the network is available.
 * @return whether device is connected or connecting to network.
 */
@SuppressLint("MissingPermission")
fun Context.isNetworkAvailable(): Boolean {
    return (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        .activeNetworkInfo?.isConnected ?: false
}

/**
 * Registers [ConnectivityManager.NetworkCallback].
 */
@SuppressLint("MissingPermission", "ObsoleteSdkInt")
fun Context.registerNetworkCallback(callback: ConnectivityManager.NetworkCallback) {
    val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= 24) {
        manager.registerDefaultNetworkCallback(callback)
    } else {
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        manager.registerNetworkCallback(request, callback)
    }
}

/**
 * Unregisters [ConnectivityManager.NetworkCallback].
 */
fun Context.unregisterNetworkCallback(callback: ConnectivityManager.NetworkCallback) {
    val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    manager.unregisterNetworkCallback(callback)
}

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
