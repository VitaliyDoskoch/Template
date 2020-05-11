package com.extensions.android_test.functions

import androidx.annotation.IntRange
import com.extensions.android_test.condition.Condition

fun createCondition(check: () -> Unit): Condition {
    return object : Condition {
        override fun isSatisfied(): Boolean {
            return try {
                check()
                true
            } catch (error: AssertionError) {
                false
            }
        }
    }
}

/**
 * Waits until [Condition] becomes satisfied. Blocks [Thread], in which is called.
 * @param [timeout] waiting timeout in millis. 2_000 is used by default.
 * @param [checkInterval] time interval in millis between each check. 250 is used by default.
 */
fun Condition.wait(@IntRange(from = 0L) timeout: Long = 2_000L,
                   @IntRange(from = 0L) checkInterval: Long = 250L) {
    var elapsedTime = 0L

    while (true) {
        if (isSatisfied()) {
            break
        } else {
            if (elapsedTime >= timeout) {
                throw IllegalStateException("Wait for condition timeout")
            } else {
                Thread.sleep(checkInterval)
                elapsedTime += checkInterval
            }
        }
    }
}