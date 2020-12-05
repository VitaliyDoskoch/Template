package com.extensions.lifecycle.functions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.extensions.lifecycle.components.DataObserver
import com.extensions.lifecycle.components.LiveDataPool
import com.extensions.lifecycle.components.ResultObserver
import com.extensions.lifecycle.components.State

fun <T : Any> LiveData<State<T>>.observeResult(owner: LifecycleOwner,
                                               liveDataPool: LiveDataPool<State<T>>? = null,
                                               action: (state: State<T>) -> Unit) {
    observe(owner, ResultObserver(this, liveDataPool, action))
}

fun <T : Any> LiveData<State<T>>.observeData(owner: LifecycleOwner,
                                             action: (state: State<T>) -> Unit) {
    observe(owner, DataObserver(action))
}