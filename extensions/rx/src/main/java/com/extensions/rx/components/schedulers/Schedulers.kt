package com.extensions.rx.components.schedulers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

val newThreadScheduler: Scheduler
    get() = Schedulers.newThread()

val ioScheduler: Scheduler
    get() = Schedulers.io()

val computationScheduler: Scheduler
    get() = Schedulers.computation()

val singleScheduler: Scheduler
    get() = Schedulers.single()

val trampolineScheduler: Scheduler
    get() = Schedulers.trampoline()

val mainThread: Scheduler
    get() = AndroidSchedulers.mainThread()