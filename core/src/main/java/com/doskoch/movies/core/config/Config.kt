package com.doskoch.movies.core.config

import android.content.Context
import com.doskoch.movies.core.R

val Context.SHORT_ANIMATION_DURATION
    get() = resources.getInteger(R.integer.short_animation_duration).toLong()
val Context.MEDIUM_ANIMATION_DURATION
    get() = resources.getInteger(R.integer.medium_animation_duration).toLong()
val Context.LONG_ANIMATION_DURATION
    get() = resources.getInteger(R.integer.long_animation_duration).toLong()