package com.extensions.kotlin.components.tuples

open class TupleOf5<T1, T2, T3, T4, T5>(
    first: T1,
    second: T2,
    third: T3,
    fourth: T4,
    var fifth: T5
) : TupleOf4<T1, T2, T3, T4>(first, second, third, fourth) {

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun <T1, T2, T3, T4, T5> fromList(list: List<Any?>): TupleOf5<T1, T2, T3, T4, T5> {
            return TupleOf5(
                list[0] as T1,
                list[1] as T2,
                list[2] as T3,
                list[3] as T4,
                list[4] as T5
            )
        }
    }

    override val elements: Int = 5

    override fun toList(): List<Any?> {
        return listOf(first, second, third, fourth, fifth)
    }

    override fun hasNullElement(): Boolean = super.hasNullElement() || fifth == null

    override fun equals(other: Any?): Boolean {
        return super.equals(other) && (other as? TupleOf5<*, *, *, *, *>)?.let { fifth == other.fifth } ?: false
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (fifth?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "($first, $second, $third, $fourth, $fifth)"
    }

    operator fun component5() = fifth
}

fun <T1, T2, T3, T4, T5> TupleOf5<T1, T2, T3, T4, T5>.reverse(): TupleOf5<T5, T4, T3, T2, T1> {
    return TupleOf5(fifth, fourth, third, second, first)
}

operator fun <T1, T2, T3, T4, T5, E> TupleOf5<T1, T2, T3, T4, T5>.plus(element: E): TupleOf6<T1, T2, T3, T4, T5, E> {
    return TupleOf6(first, second, third, fourth, fifth, element)
}

fun <T1, T2, T3, T4, T5>
    List<TupleOf5<T1, T2, T3, T4, T5>>.toTuple(): TupleOf5<List<T1>, List<T2>, List<T3>, List<T4>, List<T5>> {
    val t1 = mutableListOf<T1>()
    val t2 = mutableListOf<T2>()
    val t3 = mutableListOf<T3>()
    val t4 = mutableListOf<T4>()
    val t5 = mutableListOf<T5>()

    this.forEach {
        t1.add(it.first)
        t2.add(it.second)
        t3.add(it.third)
        t4.add(it.fourth)
        t5.add(it.fifth)
    }

    return TupleOf5(t1, t2, t3, t4, t5)
}