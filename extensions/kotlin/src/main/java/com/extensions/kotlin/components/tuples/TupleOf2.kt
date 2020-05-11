package com.extensions.kotlin.components.tuples

open class TupleOf2<T1, T2>(
    var first: T1,
    var second: T2
) : Tuple() {

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun <T1, T2> fromList(list: List<Any?>): TupleOf2<T1, T2> {
            return TupleOf2(list[0] as T1, list[1] as T2)
        }
    }

    override val elements: Int = 2

    override fun toList(): List<Any?> {
        return listOf(first, second)
    }

    override fun hasNullElement(): Boolean = first == null || second == null

    override fun equals(other: Any?): Boolean {
        return if (this === other) {
            true
        } else {
            (other as? TupleOf2<*, *>)?.let {
                when {
                    elements != other.elements -> false
                    first != other.first -> false
                    second != other.second -> false
                    else -> true
                }
            } ?: false
        }
    }

    override fun hashCode(): Int {
        var result = elements.hashCode()
        result = 31 * result + (first?.hashCode() ?: 0)
        result = 31 * result + (second?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "($first, $second)"
    }

    operator fun component1() = first
    operator fun component2() = second
}

fun <T1, T2> TupleOf2<T1, T2>.reverse(): TupleOf2<T2, T1> {
    return TupleOf2(second, first)
}

operator fun <T1, T2, E> TupleOf2<T1, T2>.plus(element: E): TupleOf3<T1, T2, E> {
    return TupleOf3(first, second, element)
}

fun <T1, T2> List<TupleOf2<T1, T2>>.toTuple(): TupleOf2<List<T1>, List<T2>> {
    val t1 = mutableListOf<T1>()
    val t2 = mutableListOf<T2>()

    this.forEach {
        t1.add(it.first)
        t2.add(it.second)
    }

    return TupleOf2(t1, t2)
}