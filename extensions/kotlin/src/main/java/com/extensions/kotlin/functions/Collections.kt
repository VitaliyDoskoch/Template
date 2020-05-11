package com.extensions.kotlin.functions

//region Array

operator fun <T> Array<T>.component6(): T = this[5]

operator fun <T> Array<T>.component7(): T = this[6]

operator fun <T> Array<T>.component8(): T = this[7]

operator fun <T> Array<T>.component9(): T = this[8]

operator fun <T> Array<T>.component10(): T = this[9]

//endregion

//region List

operator fun <T> List<T>.component6(): T = this[5]

operator fun <T> List<T>.component7(): T = this[6]

operator fun <T> List<T>.component8(): T = this[7]

operator fun <T> List<T>.component9(): T = this[8]

operator fun <T> List<T>.component10(): T = this[9]

//endregion

//region Set

operator fun <T> Set<T>.component6(): T = this.elementAt(5)

operator fun <T> Set<T>.component7(): T = this.elementAt(6)

operator fun <T> Set<T>.component8(): T = this.elementAt(7)

operator fun <T> Set<T>.component9(): T = this.elementAt(8)

operator fun <T> Set<T>.component10(): T = this.elementAt(9)

//endregion