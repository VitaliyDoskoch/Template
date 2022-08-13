package com.doskoch.template.core.components.event

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EventQueue<E> {

    private val state = MutableStateFlow(emptyList<ProcessableEvent<E>>())

    suspend fun collect(process: suspend (E) -> Unit) {
        coroutineScope {
            state.collect { events ->
                events
                    .filterNot { it.processing }
                    .forEach { event ->
                        launch {
                            try {
                                event.processing = true
                                process(event.event)
                                state.update { it - event }
                            } catch (t: Throwable) {
                                event.processing = false
                                throw t
                            }
                        }
                    }
            }
        }
    }

    fun ViewModel.add(event: E) = state.update { it + ProcessableEvent(processing = false, event) }

    private data class ProcessableEvent<E>(var processing: Boolean, val event: E)
}