package com.doskoch.template.core.kotlin.di

import com.doskoch.template.core.kotlin.lazy.DestroyableLazy

abstract class ComponentAccessor<T : Any> {
    private var provider: DestroyableLazy<T>? = null

    fun init(provider: DestroyableLazy<T>) {
        this.provider = provider
    }

    fun get(): T = requireNotNull(
        value = provider,
        lazyMessage = {
            "The Provider of this ComponentAccessor hasn't been initialized. " +
                "Call the init function within the AppInjector class before accessing the component."
        }
    ).value

    fun destroyComponent() = provider?.destroyInstance()
}
