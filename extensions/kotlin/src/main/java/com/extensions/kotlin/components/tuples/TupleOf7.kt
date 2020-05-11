package com.extensions.kotlin.components.tuples

open class TupleOf7<T1, T2, T3, T4, T5, T6, T7>(
    first: T1,
    second: T2,
    third: T3,
    fourth: T4,
    fifth: T5,
    sixth: T6,
    var seventh: T7
) : TupleOf6<T1, T2, T3, T4, T5, T6>(first, second, third, fourth, fifth, sixth) {

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun <T1, T2, T3, T4, T5, T6, T7> fromList(list: List<Any?>): TupleOf7<T1, T2, T3, T4, T5, T6, T7> {
            return TupleOf7(
                list[0] as T1, list[1] as T2, list[2] as T3, list[3] as T4, list[4] as T5,
                list[5] as T6, list[6] as T7
            )
        }
    }

    override val elements: Int = 7

    override fun toList(): List<Any?> {
        return listOf(first, second, third, fourth, fifth, sixth, seventh)
    }

    override fun hasNullElement(): Boolean = super.hasNullElement() || seventh == null

    override fun equals(other: Any?): Boolean {
        return super.equals(other) && (other as? TupleOf7<*, *, *, *, *, *, *>)
            ?.let { seventh == other.seventh } ?: false
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (seventh?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "($first, $second, $third, $fourth, $fifth, $sixth, $seventh)"
    }

    operator fun component7() = seventh
}

fun <T1, T2, T3, T4, T5, T6, T7>
    TupleOf7<T1, T2, T3, T4, T5, T6, T7>.reverse(): TupleOf7<T7, T6, T5, T4, T3, T2, T1> {
    return TupleOf7(seventh, sixth, fifth, fourth, third, second, first)
}

operator fun <T1, T2, T3, T4, T5, T6, T7, E>
    TupleOf7<T1, T2, T3, T4, T5, T6, T7>.plus(element: E): TupleOf8<T1, T2, T3, T4, T5, T6, T7, E> {
    return TupleOf8(first, second, third, fourth, fifth, sixth, seventh, element)
}

fun <T1, T2, T3, T4, T5, T6, T7>
    List<TupleOf7<T1, T2, T3, T4, T5, T6, T7>>.toTuple():
    TupleOf7<List<T1>, List<T2>, List<T3>, List<T4>, List<T5>, List<T6>, List<T7>> {
    val t1 = mutableListOf<T1>()
    val t2 = mutableListOf<T2>()
    val t3 = mutableListOf<T3>()
    val t4 = mutableListOf<T4>()
    val t5 = mutableListOf<T5>()
    val t6 = mutableListOf<T6>()
    val t7 = mutableListOf<T7>()

    this.forEach {
        t1.add(it.first)
        t2.add(it.second)
        t3.add(it.third)
        t4.add(it.fourth)
        t5.add(it.fifth)
        t6.add(it.sixth)
        t7.add(it.seventh)
    }

    return TupleOf7(t1, t2, t3, t4, t5, t6, t7)
}