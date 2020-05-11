package com.extensions.kotlin.functions

/**
 * Compares this to another [Throwable].
 * @param [other] target [Throwable].
 * @param [compareStackTrace] whether stacktrace top should be compared too.
 * False is used by default.
 * @param [compareCause] whether result should include [isSameAs] call on [Throwable.cause] too.
 * False is used by default.
 * @param [compareSuppressed] whether result should include [isSameAs] call on all of [Throwable.getSuppressed].
 * False is used by default.
 */
fun <T : Throwable> T.isSameAs(other: Throwable,
                               compareStackTrace: Boolean = false,
                               compareCause: Boolean = false,
                               compareSuppressed: Boolean = false): Boolean {
    return this === other ||
        this::class == other::class &&
        message == other.message &&
        localizedMessage == other.localizedMessage &&
        compareStackTrace.let { compare ->
            if (compare) stackTrace?.firstOrNull() == other.stackTrace.firstOrNull() else true
        } &&
        compareCause.let { compare ->
            if (compare) compareCause(other, compareStackTrace, compareSuppressed) else true
        } &&
        compareSuppressed.let { compare ->
            if (compare) compareSuppressed(other, compareStackTrace, compareCause) else true
        }
}

/**
 * Compares [Throwable.cause] of [Throwable]s. Uses [isSameAs] function for such
 * comparison.
 */
fun <T : Throwable> T.compareCause(other: Throwable,
                                   compareStackTrace: Boolean = false,
                                   compareSuppressed: Boolean = false): Boolean {
    val cause1 = this.cause
    val cause2 = other.cause

    return if (cause1 != null && cause2 != null) {
        cause1.isSameAs(cause2, compareStackTrace, true, compareSuppressed)
    } else {
        cause1 == null && cause2 == null
    }
}

/**
 * Compares [Throwable.getSuppressed] of [Throwable]s. Uses [isSameAs] function for such
 * comparison.
 */
fun <T : Throwable> T.compareSuppressed(other: Throwable,
                                        compareStackTrace: Boolean = false,
                                        compareCause: Boolean = false): Boolean {
    return if (suppressed.size == other.suppressed.size) {
        suppressed.forEachIndexed { index, suppressed ->
            val otherSuppressed = other.suppressed.getOrNull(index)
            if (otherSuppressed == null ||
                !suppressed.isSameAs(otherSuppressed, compareStackTrace, compareCause, true)
            ) {
                return false
            }
        }
        true
    } else {
        false
    }
}