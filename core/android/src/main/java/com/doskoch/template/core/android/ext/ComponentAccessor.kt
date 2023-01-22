package com.doskoch.template.core.android.ext

import com.doskoch.template.core.kotlin.di.ComponentAccessor
import dagger.hilt.EntryPoints

inline fun <reified T> ComponentAccessor<*>.entryPoint() = EntryPoints.get(get(), T::class.java)