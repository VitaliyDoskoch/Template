package com.extensions.lifecycle_test.components

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.extensions.kotlin.functions.runAndWaitForEvent
import com.extensions.lifecycle.components.State
import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.MILLISECONDS
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * It is a helper class, that allows to capture emission of [State] on passed [LiveData].
 */
class StateTestHelper<T : Any>(
    val timeout: Long,
    val timeUnit: TimeUnit,
    val getLiveData: () -> LiveData<State<T>>
) {

    constructor(getLiveData: () -> LiveData<State<T>>) : this(1_000, MILLISECONDS, getLiveData)

    inline fun <reified S : State<T>> capture(action: StateTestHelper<T>.(CapturingSlot<S>) -> Unit) {
        action(capture())
    }

    inline fun <reified S : State<T>> capture(): CapturingSlot<S> {
        val observer = mockk<Observer<State<T>>>(relaxed = true)

        runAndWaitForEvent(timeout, timeUnit) {
            every { observer.onChanged(match { it is S }) } answers { it() }
            getLiveData().observeForever(observer)
        }

        val slot = slot<S>()
        verify { observer.onChanged(capture(slot)) }
        return slot
    }

    fun <S : State<T>> CapturingSlot<S>.assertPureState() {
        assertTrue(isCaptured)
        assertFalse(captured.isConsumed)
        assertNull(captured.arguments)
    }
}