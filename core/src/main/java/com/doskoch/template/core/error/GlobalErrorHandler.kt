package com.doskoch.template.core.error

interface GlobalErrorHandler {
    fun showError(error: CoreError)
}