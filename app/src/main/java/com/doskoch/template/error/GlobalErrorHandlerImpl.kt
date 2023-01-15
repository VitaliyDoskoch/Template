package com.doskoch.template.error

import com.doskoch.template.core.components.error.CoreError
import com.doskoch.template.core.components.error.GlobalErrorHandler
import com.doskoch.template.core.components.event.EventQueue
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalErrorHandlerImpl @Inject constructor() : GlobalErrorHandler, EventQueue.Producer {

    val events = EventQueue<CoreError>()

    override fun handle(error: CoreError, showIfNotHandled: Boolean) = when (error) {
        is CoreError.OperationIsCanceled -> {
            Timber.w("Some important logic can be done here")
            false
        }
        else -> if (showIfNotHandled) {
            events.enqueue(error)
            true
        } else {
            false
        }
    }
}
