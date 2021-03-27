package com.doskoch.movies.features.main

import com.doskoch.movies.features.main.view.MainFragment

fun MainFragment.newModule() = MainFragment.Module(
    Injector.provideAllFilmsFragment,
    Injector.provideFavouriteFilmsFragment
)