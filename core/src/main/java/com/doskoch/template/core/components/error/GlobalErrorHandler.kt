package com.doskoch.template.core.components.error

interface GlobalErrorHandler {
    fun handle(error: CoreError, showIfNotHandled: Boolean = true): Boolean
}
