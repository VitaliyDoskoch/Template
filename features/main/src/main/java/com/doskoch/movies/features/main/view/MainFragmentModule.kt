package com.doskoch.movies.features.main.view

import androidx.fragment.app.Fragment
import com.doskoch.movies.features.main.Injector

class MainFragmentModule {

    companion object {
        fun create(): MainFragmentModule {
            return MainFragmentModule()
        }
    }

    fun provideAllFilmsFragment(): Fragment {
        return Injector.component.provideAllFilmsFragment()
    }

    fun provideFavouriteFilmsFragment(): Fragment {
        return Injector.component.provideFavouriteFilmsFragment()
    }
}