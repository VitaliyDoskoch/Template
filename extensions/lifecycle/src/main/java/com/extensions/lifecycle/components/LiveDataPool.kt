package com.extensions.lifecycle.components

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

class LiveDataPool<T : Any> {

    private val setData by lazy { MutableLiveData<Set<LiveData<T>>>() }

    fun get(): LiveData<Set<LiveData<T>>> = setData

    fun observe(owner: LifecycleOwner, mapper: (LiveData<T>) -> Unit) {
        setData.observe(owner, Observer { set ->
            set
                .filter { !it.hasActiveObservers() }
                .forEach(mapper)
        })
    }

    @MainThread
    internal fun add(liveData: LiveData<T>) {
        setData.value = (setData.value ?: setOf()) + liveData
    }

    @MainThread
    fun remove(liveData: LiveData<T>) {
        setData.value = setData.value?.let { it - liveData }
    }
}

@MainThread
fun <T : Any> ViewModel.add(pool: LiveDataPool<T>, liveData: LiveData<T>) {
    pool.add(liveData)
}