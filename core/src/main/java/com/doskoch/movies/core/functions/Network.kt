package com.doskoch.movies.core.functions

// fun Context.waitForNetwork(): Completable {
//    return Completable.create { emitter ->
//        try {
//            val callback = object : ConnectivityManager.NetworkCallback() {
//                override fun onAvailable(network: Network) {
//                    emitter.onComplete()
//                }
//            }
//
//            emitter.setCancellable { unregisterNetworkCallback(callback) }
//
//            registerNetworkCallback(callback)
//        } catch (throwable: Throwable) {
//            if (!emitter.isDisposed) {
//                emitter.onError(throwable)
//            }
//        }
//    }
// }
