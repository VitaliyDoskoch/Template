package com.extensions.kotlin.components.tuples

open class TupleOf4<T1, T2, T3, T4>(
    first: T1,
    second: T2,
    third: T3,
    var fourth: T4
) : TupleOf3<T1, T2, T3>(first, second, third) {

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun <T1, T2, T3, T4> fromList(list: List<Any?>): TupleOf4<T1, T2, T3, T4> {
            return TupleOf4(list[0] as T1, list[1] as T2, list[2] as T3, list[3] as T4)
        }
    }

    override val elements: Int = 4

    override fun toList(): List<Any?> {
        return listOf(first, second, third, fourth)
    }

    override fun hasNullElement(): Boolean = super.hasNullElement() || fourth == null

    override fun equals(other: Any?): Boolean {
        return super.equals(other) && (other as? TupleOf4<*, *, *, *>)?.let { fourth == other.fourth } ?: false
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (fourth?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "($first, $second, $third, $fourth)"
    }

    operator fun component4() = fourth
}

fun <T1, T2, T3, T4> TupleOf4<T1, T2, T3, T4>.reverse(): TupleOf4<T4, T3, T2, T1> {
    return TupleOf4(fourth, third, second, first)
}

operator fun <T1, T2, T3, T4, E> TupleOf4<T1, T2, T3, T4>.plus(element: E): TupleOf5<T1, T2, T3, T4, E> {
    return TupleOf5(first, second, third, fourth, element)
}

fun <T1, T2, T3, T4> List<TupleOf4<T1, T2, T3, T4>>.toTuple(): TupleOf4<List<T1>, List<T2>, List<T3>, List<T4>> {
    val t1 = mutableListOf<T1>()
    val t2 = mutableListOf<T2>()
    val t3 = mutableListOf<T3>()
    val t4 = mutableListOf<T4>()

    this.forEach {
        t1.add(it.first)
        t2.add(it.second)
        t3.add(it.third)
        t4.add(it.fourth)
    }

    return TupleOf4(t1, t2, t3, t4)
}