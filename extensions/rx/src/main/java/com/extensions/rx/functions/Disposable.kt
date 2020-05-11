package com.extensions.rx.functions

import io.reactivex.disposables.Disposable

/**
 * Recreates [Disposable] on trigger function invocation.
 * @param [create] function, which gets called instantly and creates [Disposable],
 * providing trigger function.
 * When consumer calls that trigger function, [create] function is invoked again.
 */
fun recreateOnEvent(create: (() -> Unit) -> Disposable) {

    fun newEventHandler(): () -> Unit = { create(newEventHandler()) }

    create(newEventHandler())
}