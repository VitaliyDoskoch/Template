package com.extensions.kotlin.components.annotations

import kotlin.reflect.KClass

/**
 * Simple annotation for code readability. Does nothing.
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.SOURCE)
annotation class CallsFrom(vararg val callers: KClass<*>)