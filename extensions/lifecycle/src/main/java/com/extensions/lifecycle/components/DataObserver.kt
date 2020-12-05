package com.extensions.lifecycle.components

import android.util.Log
import androidx.lifecycle.Observer

/**
 * It is an [Observer] implementation, which modifies [State.isConsumed] and considers if
 * emission of [State.Failure] should trigger [action].
 */
class DataObserver<T : Any>(private val action: (state: State<T>) -> Unit) : Observer<State<T>> {

    private var previousFailure: State.Failure<T>? = null

    override fun onChanged(state: State<T>?) {
        when (state) {
            is State.Loading, is State.Success -> action(state).also {
                state.isConsumed = true
                previousFailure = null
            }
            is State.Failure -> if (
                previousFailure?.data != state.data ||
                previousFailure?.throwable?.isSameAs(state.throwable) != true
            ) {
                action(state).also {
                    state.isConsumed = true
                    previousFailure = state
                }
            }
        }
    }

    private fun <T : Throwable> T.isSameAs(other: Throwable): Boolean {
        return this === other ||
            this::class == other::class &&
            message == other.message &&
            localizedMessage == other.localizedMessage
    }
}