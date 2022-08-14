package com.doskoch.template

import com.doskoch.template.core.components.error.CoreError
import com.doskoch.template.core.components.error.GlobalErrorHandler
import com.doskoch.template.core.components.event.EventQueue
import timber.log.Timber

class GlobalErrorHandlerImpl : GlobalErrorHandler, EventQueue.Producer {

    val events = EventQueue<CoreError>()

    override fun handle(error: CoreError): Boolean {
        if(error is CoreError.Unknown) {
            Timber.w("$error")
        }
        events.add(error)
        return true
    }
}