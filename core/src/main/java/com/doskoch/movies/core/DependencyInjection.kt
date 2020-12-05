package com.doskoch.movies.core

import android.content.Context

internal typealias Injector = CoreInjector

interface CoreComponent {
    val context: Context
}

object CoreInjector {
    lateinit var component: CoreComponent
}