package com.extensions.lifecycle.components

sealed class State<T : Any> {

    var isConsumed: Boolean = false

    abstract val data: T?
    abstract val arguments: Any?

    class Loading<T : Any>(
        override val data: T? = null,
        override val arguments: Any? = null
    ) : State<T>()

    class Success<T : Any>(
        override val data: T,
        override val arguments: Any? = null
    ) : State<T>()

    class Failure<T : Any>(
        val throwable: Throwable,
        override val data: T? = null,
        override val arguments: Any? = null
    ) : State<T>()
}