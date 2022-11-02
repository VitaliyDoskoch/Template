package com.doskoch.template.core.components.error

interface GlobalErrorHandler {
    /**
     * @param showIfNotHandled whether to show the error on the ui if no specific action is required
     * @return false if no action is performed
     */
    fun handle(error: CoreError, showIfNotHandled: Boolean = true): Boolean
}
