package com.doskoch.template.api.jikan

interface JikanApi

object JikanApiInjector {
    lateinit var component: JikanApi
}

internal val Injector: JikanApi
    get() = JikanApiInjector.component
