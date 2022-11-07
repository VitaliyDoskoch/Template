package com.doskoch.template.core.di

object CoreInjector {
    lateinit var component: CoreComponent
}

internal val Injector: CoreComponent
    get() = CoreInjector.component
