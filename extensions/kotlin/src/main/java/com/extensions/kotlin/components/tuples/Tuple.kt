package com.extensions.kotlin.components.tuples

import java.io.Serializable

abstract class Tuple : Serializable {
    abstract val elements: Int

    abstract fun toList(): List<Any?>
    abstract fun hasNullElement(): Boolean

    abstract override fun equals(other: Any?): Boolean
    abstract override fun hashCode(): Int
    abstract override fun toString(): String
}