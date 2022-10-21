package com.doskoch.template.di.modules

import com.doskoch.template.api.jikan.JikanApi
import com.doskoch.template.di.AppComponent

fun jikanApiModule(component: AppComponent) = object : JikanApi {}
