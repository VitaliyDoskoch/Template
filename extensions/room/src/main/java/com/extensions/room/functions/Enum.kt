package com.extensions.room.functions

inline fun <reified T : Enum<T>> T.fromEnum(): Int = this.ordinal

inline fun <reified T : Enum<T>> Int.toEnum(): T = enumValues<T>()[this]