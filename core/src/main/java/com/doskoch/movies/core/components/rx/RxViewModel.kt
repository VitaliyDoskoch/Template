package com.doskoch.movies.core.components.rx

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.extensions.lifecycle.components.LiveDataPool
import com.extensions.lifecycle.components.add
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class RxViewModel : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    private val observers by lazy { mutableMapOf<LiveData<*>, Pair<Disposable, () -> Disposable>>() }

    fun <T : Any> LiveDataPool<T>.add(liveData: LiveData<T>) {
        add(this, liveData)
    }

    /**
     * Will dispose passed Disposables, when [onCleared] method will be called.
     */
    fun disposeOnCleared(vararg disposables: Disposable) {
        compositeDisposable.addAll(*disposables)
    }

    /**
     * Associates Disposable with passed LiveData.
     * @param liveData LiveData, that is used by observer to notify View.
     * @param create function, that creates Disposable. Will be called only if there is no already
     * associated Disposable or it is already disposed.
     * NOTE: Should be used to avoid multiple Disposables creation after every View recreation
     * (for data sources).
     */
    fun addObserver(liveData: LiveData<*>, create: () -> Disposable) {
        val existing = observers[liveData]
        if (existing == null || existing.first.isDisposed) {
            observers[liveData] = create() to create
        }
    }

    /**
     * Disposes Disposable, associated with passed LiveData.
     */
    fun removeObserver(liveData: LiveData<*>) {
        observers[liveData]?.first?.dispose()
        observers.remove(liveData)
    }

    /**
     * Recreates Disposables for every added observer. Previous Disposables will be disposed.
     */
    fun recreateObservers() {
        val newObservers = observers.map { (liveData, pair) ->
            liveData to Pair(pair.second.invoke(), pair.second)
        }
        clearObservers()
        observers.putAll(newObservers)
    }

    /**
     * Clears added observers and disposes their Disposables.
     */
    fun clearObservers() {
        observers.forEach { (_, pair) -> pair.first.dispose() }
        observers.clear()
    }

    /**
     * Disposes CompositeDisposable and clears added observers.
     */
    @CallSuper
    public override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
        observers.clear()
    }
}