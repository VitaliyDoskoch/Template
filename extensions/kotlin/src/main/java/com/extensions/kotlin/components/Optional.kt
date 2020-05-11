package com.extensions.kotlin.components

/**
 * [java.util.Optional] could be used only on api 24 and above, so this [Optional] is a
 * workaround. If min SDK is 24, java's implementation could be used instead.
 */
data class Optional<T : Any>(var value: T?)