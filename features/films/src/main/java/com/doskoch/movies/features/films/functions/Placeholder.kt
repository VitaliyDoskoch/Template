package com.doskoch.movies.features.films.functions

import com.doskoch.movies.core.components.ui.base.fragment.BaseFragment
import com.doskoch.movies.core.components.ui.views.CorePlaceholder
import com.doskoch.movies.core.functions.showSimple
import com.doskoch.movies.features.films.R

fun BaseFragment.showNoFilmsPlaceholder() {
    contentManager?.showPlaceholder { view ->
        (view as? CorePlaceholder)?.let {
            it.showSimple(
                CorePlaceholder.Image(R.drawable.ic_placeholder_no_films, "1:1"),
                it.context.getString(R.string.no_films)
            )
        }
    }
}