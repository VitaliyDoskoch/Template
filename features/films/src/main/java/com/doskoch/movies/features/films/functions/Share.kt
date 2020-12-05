package com.doskoch.movies.features.films.functions

import androidx.lifecycle.Lifecycle
import com.doskoch.movies.core.components.ui.base.fragment.BaseFragment
import com.doskoch.movies.database.modules.films.view.Film
import com.extensions.rx.components.schedulers.ioScheduler
import com.extensions.rx.components.schedulers.mainThread
import timber.log.Timber

fun BaseFragment<*>.shareFilm(item: Film, showError: (throwable: Throwable) -> Unit) {
    val context = context ?: return

    context.preparePosterForSharing(item.posterPath)
        .subscribeOn(ioScheduler)
        .observeOn(mainThread)
        .doOnComplete {
            /* is placed here due to handling possible error in onError block */
            context.shareFilm(item)
        }
        .subscribe({}, {
            Timber.e(it)
            showError(it)
        })
}