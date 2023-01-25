package com.doskoch.template.core.android.ui.paging

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.net.Network
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.doskoch.template.core.android.components.error.CoreError
import com.doskoch.template.core.android.di.errorMapper
import com.doskoch.template.core.android.ext.registerNetworkCallback
import com.doskoch.template.core.android.ext.unregisterNetworkCallback

@Composable
fun <T : Any> LazyPagingItems<T>.retryWhenNetworkAvailable() = this.apply {
    loadState.refresh.whenNetworkAvailable(this::retry)
    loadState.prepend.whenNetworkAvailable(this::retry)
    loadState.append.whenNetworkAvailable(this::retry)
}

@SuppressLint("ComposableNaming")
@Composable
private fun LoadState.whenNetworkAvailable(action: () -> Unit) {
    if (this is LoadState.Error && LocalContext.current.errorMapper().toCoreError(error) is CoreError.NoInternet) {
        val context = LocalContext.current

        DisposableEffect(this) {
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    context.unregisterNetworkCallback(this)
                    action()
                }
            }

            context.registerNetworkCallback(callback)

            onDispose { context.unregisterNetworkCallback(callback) }
        }
    }
}
