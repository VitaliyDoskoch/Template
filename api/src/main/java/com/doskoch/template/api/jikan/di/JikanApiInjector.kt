package com.doskoch.template.api.jikan.di

object JikanApiInjector {
    lateinit var component: JikanApiComponent
}

internal val Injector: JikanApiComponent
    get() = JikanApiInjector.component
