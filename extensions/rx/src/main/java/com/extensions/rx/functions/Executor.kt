package com.extensions.rx.functions

import io.reactivex.Scheduler
import java.util.concurrent.Executor

fun Scheduler.createExecutor(): Executor {
    val worker = createWorker()
    return Executor { worker.schedule(it) }
}