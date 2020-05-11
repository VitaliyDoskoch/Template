package com.doskoch.movies.dependencyInjection.modules

import android.content.Context
import com.doskoch.movies.core.CoreComponent
import com.doskoch.movies.dependencyInjection.AppComponent

class CoreModule(
    override val context: Context
) : CoreComponent {
    companion object {
        fun create(component: AppComponent): CoreModule {
            return CoreModule(
                context = component.application
            )
        }
    }
}