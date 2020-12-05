package com.extensions.rx.functions

import io.reactivex.CompletableEmitter
import io.reactivex.FlowableEmitter
import io.reactivex.FlowableOnSubscribe
import io.reactivex.MaybeEmitter
import io.reactivex.SingleEmitter

fun <T> FlowableEmitter<T>.applyIfActive(action: FlowableEmitter<T>.() -> Unit) {
    if (!isCancelled) {
        action()
    }
}

fun <T> SingleEmitter<T>.applyIfActive(action: SingleEmitter<T>.() -> Unit) {
    if (!isDisposed) {
        action()
    }
}

fun <T> MaybeEmitter<T>.applyIfActive(action: MaybeEmitter<T>.() -> Unit) {
    if (!isDisposed) {
        action()
    }
}

fun CompletableEmitter.applyIfActive(action: CompletableEmitter.() -> Unit) {
    if (!isDisposed) {
        action()
    }
}