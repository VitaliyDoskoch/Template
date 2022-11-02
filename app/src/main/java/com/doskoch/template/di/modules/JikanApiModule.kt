package com.doskoch.template.di.modules

import com.doskoch.template.api.jikan.di.JikanApiComponent
import com.doskoch.template.di.AppComponent

fun jikanApiModule(component: AppComponent) = object : JikanApiComponent {}
