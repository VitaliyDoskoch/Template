package com.doskoch.template.core.android.components.error

interface ErrorMapper {
    fun toCoreError(throwable: Throwable, ifUnknown: (Throwable) -> CoreError): CoreError
    fun toCoreError(throwable: Throwable, ifUnknown: CoreError = CoreError.Unknown): CoreError = toCoreError(throwable) { ifUnknown }
}
