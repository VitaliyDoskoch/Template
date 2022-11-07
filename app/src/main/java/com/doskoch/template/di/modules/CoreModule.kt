package com.doskoch.template.di.modules

import com.doskoch.template.core.di.CoreComponent
import com.doskoch.template.di.AppComponent

fun coreModule(component: AppComponent) = object : CoreComponent {
    override val authDataStore = component.authDataStore
}