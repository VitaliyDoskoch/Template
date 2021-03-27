package com.doskoch.movies.features.splash.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doskoch.movies.core.components.rx.RxViewModel
import com.extensions.lifecycle.components.State
import com.extensions.rx.components.schedulers.mainThread
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SplashViewModel(private val module: Module) : RxViewModel() {

    data class Module(val minDisplayTime: Long)

    private val displayTimeoutData by lazy { MutableLiveData<State<Any>>() }

    fun displayTimeoutData(): LiveData<State<Any>> {
        return displayTimeoutData.also {
            addObserver(it, this::observeTimeout)
        }
    }

    private fun observeTimeout(): Disposable {
        return Flowable.timer(module.minDisplayTime, TimeUnit.MILLISECONDS)
            .mergeWith(Flowable.never())
            .observeOn(mainThread)
            .doOnSubscribe {
                displayTimeoutData.value = State.Loading()
            }
            .subscribe({
                displayTimeoutData.value = State.Success(Any())
            }, {
                Timber.e(it)
                displayTimeoutData.value = State.Failure(it)
            })
            .also { disposeOnCleared(it) }
    }
}