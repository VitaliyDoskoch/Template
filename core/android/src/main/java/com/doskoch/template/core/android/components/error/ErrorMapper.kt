package com.doskoch.template.core.android.components.error

fun interface ErrorMapper {
    fun map(throwable: Throwable, ifUnknown: (Throwable) -> CoreError): CoreError
}
