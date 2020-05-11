package com.extensions.room.components

import kotlin.reflect.KClass

/**
 * It is a helper class for efficient entities selection in response to changes.
 * @param [entities] array of database entities.
 * Specifies which entities' changes should trigger select functions.
 * @param [selectors] array of select functions for every entity.
 * Order of functions should exactly match entities param.
 * @param [mapper] function, which converts array of result values to expected type.
 * Order of result values matches to selectors param.
 */
class AggregationSelector<T>(
    private val entities: Array<KClass<out Any>>,
    private val selectors: Array<() -> Any>,
    private val mapper: (values: Array<out Any>) -> T
) {

    private val lastValues = arrayOfNulls<Any?>(selectors.size)

    fun select(tables: Set<KClass<out Any>>): T {
        val values = selectors.mapIndexed { index, selector ->
            val lastValue = lastValues[index]

            if (lastValue == null || tables.contains(entities[index])) {
                selector.invoke().also { lastValues[index] = it }
            } else {
                lastValue
            }
        }

        return mapper(values.toTypedArray())
    }
}