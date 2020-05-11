package com.extensions.kotlin.components.tuples

open class TupleOf9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(
    first: T1,
    second: T2,
    third: T3,
    fourth: T4,
    fifth: T5,
    sixth: T6,
    seventh: T7,
    eighth: T8,
    var ninth: T9
) : TupleOf8<T1, T2, T3, T4, T5, T6, T7, T8>(
    first,
    second,
    third,
    fourth,
    fifth,
    sixth,
    seventh,
    eighth
) {

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9>
            fromList(list: List<Any?>): TupleOf9<T1, T2, T3, T4, T5, T6, T7, T8, T9> {
            return TupleOf9(
                list[0] as T1, list[1] as T2, list[2] as T3, list[3] as T4, list[4] as T5,
                list[5] as T6, list[6] as T7, list[7] as T8, list[8] as T9
            )
        }
    }

    override val elements: Int = 9

    override fun toList(): List<Any?> {
        return listOf(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth)
    }

    override fun hasNullElement(): Boolean = super.hasNullElement() || ninth == null

    override fun equals(other: Any?): Boolean {
        return super.equals(other) && (other as? TupleOf9<*, *, *, *, *, *, *, *, *>)
            ?.let { ninth == other.ninth } ?: false
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (ninth?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth)"
    }

    operator fun component9() = ninth
}

fun <T1, T2, T3, T4, T5, T6, T7, T8, T9>
    TupleOf9<T1, T2, T3, T4, T5, T6, T7, T8, T9>.reverse(): TupleOf9<T9, T8, T7, T6, T5, T4, T3, T2, T1> {
    return TupleOf9(ninth, eighth, seventh, sixth, fifth, fourth, third, second, first)
}

operator fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, E>
    TupleOf9<T1, T2, T3, T4, T5, T6, T7, T8, T9>.plus(element: E): TupleOf10<T1, T2, T3, T4, T5, T6, T7, T8, T9, E> {
    return TupleOf10(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, element)
}

fun <T1, T2, T3, T4, T5, T6, T7, T8, T9>
    List<TupleOf9<T1, T2, T3, T4, T5, T6, T7, T8, T9>>.toTuple():
    TupleOf9<List<T1>, List<T2>, List<T3>, List<T4>, List<T5>, List<T6>, List<T7>, List<T8>, List<T9>> {
    val t1 = mutableListOf<T1>()
    val t2 = mutableListOf<T2>()
    val t3 = mutableListOf<T3>()
    val t4 = mutableListOf<T4>()
    val t5 = mutableListOf<T5>()
    val t6 = mutableListOf<T6>()
    val t7 = mutableListOf<T7>()
    val t8 = mutableListOf<T8>()
    val t9 = mutableListOf<T9>()

    this.forEach {
        t1.add(it.first)
        t2.add(it.second)
        t3.add(it.third)
        t4.add(it.fourth)
        t5.add(it.fifth)
        t6.add(it.sixth)
        t7.add(it.seventh)
        t8.add(it.eighth)
        t9.add(it.ninth)
    }

    return TupleOf9(t1, t2, t3, t4, t5, t6, t7, t8, t9)
}