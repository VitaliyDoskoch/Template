package com.doskoch.movies.core.components.rx

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.Lifecycle.Event.ON_ANY
import androidx.lifecycle.Lifecycle.Event.ON_CREATE
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.Lifecycle.Event.ON_PAUSE
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.Lifecycle.Event.ON_START
import androidx.lifecycle.Lifecycle.Event.ON_STOP
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class DisposablesManager(val provideLifecycle: () -> Lifecycle) : LifecycleObserver {

    val compositeDisposables = mutableMapOf<Event, CompositeDisposable>()

    private var lifecycle: Lifecycle? = null

    fun disposeOn(event: Event, disposable: Disposable) {
        startObservingLifecycle()

        var compositeDisposable = compositeDisposables[event]

        if (compositeDisposable == null || compositeDisposable.isDisposed) {
            compositeDisposable = CompositeDisposable()
            compositeDisposables[event] = compositeDisposable
        }

        compositeDisposable.add(disposable)
    }

    private fun startObservingLifecycle() {
        if (lifecycle == null) {
            lifecycle = provideLifecycle().also { it.addObserver(this) }
        }
    }

    fun disposeUpToEvent(event: Event) {
        compositeDisposables
            .filterKeys {
                if (event == ON_ANY) {
                    it == ON_ANY
                } else {
                    it.ordinal <= event.ordinal
                }
            }
            .forEach { it.value.dispose() }
    }

    @OnLifecycleEvent(ON_CREATE)
    fun onCreate() {
        disposeUpToEvent(ON_CREATE)
    }

    @OnLifecycleEvent(ON_START)
    fun onStart() {
        disposeUpToEvent(ON_START)
    }

    @OnLifecycleEvent(ON_RESUME)
    fun onResume() {
        disposeUpToEvent(ON_RESUME)
    }

    @OnLifecycleEvent(ON_PAUSE)
    fun onPause() {
        disposeUpToEvent(ON_PAUSE)
    }

    @OnLifecycleEvent(ON_STOP)
    fun onStop() {
        disposeUpToEvent(ON_STOP)
    }

    @OnLifecycleEvent(ON_DESTROY)
    fun onDestroy() {
        disposeUpToEvent(ON_DESTROY)
    }

    @OnLifecycleEvent(ON_ANY)
    fun onAny() {
        disposeUpToEvent(ON_ANY)
    }
}