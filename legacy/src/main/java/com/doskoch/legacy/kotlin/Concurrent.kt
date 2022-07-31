package com.doskoch.legacy.kotlin

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Runs the [action] and waits for an invocation of the onComplete.
 */
fun runAndWaitForEvent(action: (onComplete: () -> Unit) -> Unit) {

    fun newEventHandler(latch: CountDownLatch): () -> Unit = { latch.countDown() }

    val latch = CountDownLatch(1)
    action(newEventHandler(latch))
    latch.await()
}

/**
 * Runs the [action] and waits for an invocation of of the onComplete.
 */
fun runAndWaitForEvent(timeout: Long, timeUnit: TimeUnit, action: (onComplete: () -> Unit) -> Unit) {

    fun newEventHandler(latch: CountDownLatch): () -> Unit = { latch.countDown() }

    val latch = CountDownLatch(1)
    action(newEventHandler(latch))
    latch.await(timeout, timeUnit)
}
