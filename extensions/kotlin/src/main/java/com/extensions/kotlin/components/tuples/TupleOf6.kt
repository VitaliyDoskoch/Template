package com.extensions.kotlin.components.tuples

open class TupleOf6<T1, T2, T3, T4, T5, T6>(
    first: T1,
    second: T2,
    third: T3,
    fourth: T4,
    fifth: T5,
    var sixth: T6
) : TupleOf5<T1, T2, T3, T4, T5>(first, second, third, fourth, fifth) {

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun <T1, T2, T3, T4, T5, T6> fromList(list: List<Any?>): TupleOf6<T1, T2, T3, T4, T5, T6> {
            return TupleOf6(
                list[0] as T1, list[1] as T2, list[2] as T3, list[3] as T4, list[4] as T5,
                list[5] as T6
            )
        }
    }

    override val elements: Int = 6

    override fun toList(): List<Any?> {
        return listOf(first, second, third, fourth, fifth, sixth)
    }

    override fun hasNullElement(): Boolean = super.hasNullElement() || sixth == null

    override fun equals(other: Any?): Boolean {
        return super.equals(other) && (other as? TupleOf6<*, *, *, *, *, *>)?.let { sixth == other.sixth } ?: false
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (sixth?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "($first, $second, $third, $fourth, $fifth, $sixth)"
    }

    operator fun component6() = sixth
}

fun <T1, T2, T3, T4, T5, T6> TupleOf6<T1, T2, T3, T4, T5, T6>.reverse(): TupleOf6<T6, T5, T4, T3, T2, T1> {
    return TupleOf6(sixth, fifth, fourth, third, second, first)
}

operator fun <T1, T2, T3, T4, T5, T6, E>
    TupleOf6<T1, T2, T3, T4, T5, T6>.plus(element: E): TupleOf7<T1, T2, T3, T4, T5, T6, E> {
    return TupleOf7(first, second, third, fourth, fifth, sixth, element)
}

fun <T1, T2, T3, T4, T5, T6>
    List<TupleOf6<T1, T2, T3, T4, T5, T6>>.toTuple(): TupleOf6<List<T1>, List<T2>, List<T3>, List<T4>, List<T5>, List<T6>> {
    val t1 = mutableListOf<T1>()
    val t2 = mutableListOf<T2>()
    val t3 = mutableListOf<T3>()
    val t4 = mutableListOf<T4>()
    val t5 = mutableListOf<T5>()
    val t6 = mutableListOf<T6>()

    this.forEach {
        t1.add(it.first)
        t2.add(it.second)
        t3.add(it.third)
        t4.add(it.fourth)
        t5.add(it.fifth)
        t6.add(it.sixth)
    }

    return TupleOf6(t1, t2, t3, t4, t5, t6)
}