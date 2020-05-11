package com.doskoch.movies.core.functions

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import com.extensions.android.functions.registerNetworkCallback
import com.extensions.android.functions.unregisterNetworkCallback
import io.reactivex.Completable

fun Context.waitForNetwork(): Completable {
    return Completable.create { emitter ->
        try {
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    emitter.onComplete()
                }
            }

            emitter.setCancellable { unregisterNetworkCallback(callback) }

            registerNetworkCallback(callback)
        } catch (throwable: Throwable) {
            if (!emitter.isDisposed) {
                emitter.onError(throwable)
            }
        }
    }
}