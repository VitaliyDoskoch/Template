package com.extensions.kotlin.components.di

import com.extensions.kotlin.components.DestroyableLazy

/**
 * It provides the possibility to control component lifecycle, using [DestroyableLazy] as component
 * provider.
 */
open class ComponentInjector<C> {

    var componentProvider: DestroyableLazy<out C>? = null
        set(value) {
            field?.destroyInstance()
            field = value
        }

    val component: C
        get() = componentProvider!!.value
}