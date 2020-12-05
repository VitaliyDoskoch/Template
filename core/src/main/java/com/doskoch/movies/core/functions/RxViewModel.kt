package com.doskoch.movies.core.functions

import androidx.lifecycle.MutableLiveData
import com.doskoch.movies.core.components.rx.RxViewModel
import com.extensions.lifecycle.components.State
import com.extensions.rx.components.schedulers.mainThread
import io.reactivex.Flowable
import io.reactivex.Notification
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import timber.log.Timber

fun <R : Any> RxViewModel.simpleRxAction(result: MutableLiveData<State<R>>,
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

fun <R : Any> RxViewModel.materializedRxAction(result: MutableLiveData<State<R>>,
                                               scheduler: Scheduler,
                                               action: () -> Flowable<Notification<R>>): Disposable {
    return action()
        .subscribeOn(scheduler)
        .observeOn(mainThread)
        .doOnSubscribe {
            result.value = State.Loading()
        }
        .subscribe({ notification ->
            when {
                notification.isOnNext -> {
                    result.value = State.Success(notification.value!!)
                }
                notification.isOnError -> notification.error!!.let {
                    Timber.e(it)
                    result.value = State.Failure(it)
                }
            }
        }, {
            Timber.e(it)
            result.value = State.Failure(it)
        })
        .also { disposeOnCleared(it) }
}