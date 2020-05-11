package com.extensions.rx.functions

import io.reactivex.Flowable
import io.reactivex.Notification

fun <T, R> Flowable<Notification<T>>.map(mapper: (value: T) -> R): Flowable<Notification<R>> {
    return this.map {
        when {
            it.isOnNext -> Notification.createOnNext(mapper(it.value!!))
            it.isOnError -> Notification.createOnError(it.error!!)
            else -> Notification.createOnComplete()
        }
    }
}