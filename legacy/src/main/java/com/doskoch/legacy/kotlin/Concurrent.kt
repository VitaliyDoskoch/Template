package com.doskoch.legacy.kotlin

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Runs [action] and waits for an invocation of it's parameter, that is a trigger function.
 */
fun runAndWaitForEvent(action: (() -> Unit) -> Unit) {

    fun newEventHandler(latch: CountDownLatch): () -> Unit = { latch.countDown() }

    val latch = CountDownLatch(1)
    action(newEventHandler(latch))
    latch.await()
}

/**
 * Runs [action] and waits for an invocation of it's parameter, that is a trigger function.
 */
fun runAndWaitForEvent(timeout: Long, timeUnit: TimeUnit, action: (() -> Unit) -> Unit) {

    fun newEventHandler(latch: CountDownLatch): () -> Unit = { latch.countDown() }

    val latch = CountDownLatch(1)
    action(newEventHandler(latch))
    latch.await(timeout, timeUnit)
}