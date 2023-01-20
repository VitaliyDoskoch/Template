package com.doskoch.template.api.jikan.di.qualifiers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GsonForSerializing

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GsonForLogging