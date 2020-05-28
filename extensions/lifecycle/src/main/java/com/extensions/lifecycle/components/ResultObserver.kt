package com.extensions.lifecycle.components

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * It is an [Observer] implementation, which stops observing data on
 * [State.Success] or [State.Failure] emission.
 */
class ResultObserver<T : Any>(
    private val liveData: LiveData<State<T>>,
    private val action: (state: State<T>) -> Unit
) : Observer<State<T>> {

    override fun onChanged(state: State<T>?) {
        if (state != null) {
            when (state) {
                is State.Loading -> action(state).also {
                    state.isConsumed = true
                }
                is State.Success -> action(state).also {
                    state.isConsumed = true
                    liveData.removeObserver(this)
                }
                is State.Failure -> action(state).also {
                    state.isConsumed = true
                    liveData.removeObserver(this)
                }
            }
        }
    }
}