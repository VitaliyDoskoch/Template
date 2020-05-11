package com.doskoch.movies.core.functions

import androidx.lifecycle.MutableLiveData
import com.doskoch.movies.core.components.rx.RxViewModel
import com.extensions.lifecycle.components.State
import com.extensions.rx.components.schedulers.mainThread
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import timber.log.Timber

fun <R : Any> RxViewModel.runSimpleRxAction(result: MutableLiveData<State<R>>,
                                            scheduler: Scheduler,
                                            action: () -> Flowable<R>): Disposable {
    return action()
        .subscribeOn(scheduler)
        .observeOn(mainThread)
        .doOnSubscribe {
            result.value = State.Loading()
        }
        .subscribe({
            result.value = State.Success(it)
        }, {
            Timber.e(it)
            result.value = State.Failure(it)
        })
        .also { disposeOnCleared(it) }
}