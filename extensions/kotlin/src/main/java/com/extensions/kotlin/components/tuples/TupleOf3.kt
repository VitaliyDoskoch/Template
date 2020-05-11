package com.extensions.kotlin.components.tuples

open class TupleOf3<T1, T2, T3>(
    first: T1,
    second: T2,
    var third: T3
) : TupleOf2<T1, T2>(first, second) {

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun <T1, T2, T3> fromList(list: List<Any?>): TupleOf3<T1, T2, T3> {
            return TupleOf3(list[0] as T1, list[1] as T2, list[2] as T3)
        }
    }

    override val elements: Int = 3

    override fun toList(): List<Any?> {
        return listOf(first, second, third)
    }

    override fun hasNullElement(): Boolean = super.hasNullElement() || third == null

    override fun equals(other: Any?): Boolean {
        return super.equals(other) && (other as? TupleOf3<*, *, *>)?.let { third == other.third } ?: false
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (third?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "($first, $second, $third)"
    }

    operator fun component3() = third
}

fun <T1, T2, T3> TupleOf3<T1, T2, T3>.reverse(): TupleOf3<T3, T2, T1> {
    return TupleOf3(third, second, first)
}

operator fun <T1, T2, T3, E> TupleOf3<T1, T2, T3>.plus(element: E): TupleOf4<T1, T2, T3, E> {
    return TupleOf4(first, second, third, element)
}

fun <T1, T2, T3> List<TupleOf3<T1, T2, T3>>.toTuple(): TupleOf3<List<T1>, List<T2>, List<T3>> {
    val t1 = mutableListOf<T1>()
    val t2 = mutableListOf<T2>()
    val t3 = mutableListOf<T3>()

    this.forEach {
        t1.add(it.first)
        t2.add(it.second)
        t3.add(it.third)
    }

    return TupleOf3(t1, t2, t3)
}