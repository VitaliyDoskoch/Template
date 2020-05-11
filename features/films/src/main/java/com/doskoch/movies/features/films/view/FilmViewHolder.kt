package com.doskoch.movies.features.films.view

import android.view.View
import android.widget.Button
import androidx.annotation.IdRes
import androidx.transition.ChangeBounds
import androidx.transition.TransitionSet
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.doskoch.movies.core.components.ui.base.recyclerView.BaseViewHolder
import com.doskoch.movies.core.config.SHORT_ANIMATION_DURATION
import com.doskoch.movies.database.modules.films.view.Film
import com.doskoch.movies.features.films.R
import com.doskoch.movies.features.films.databinding.ListItemFilmBinding
import com.doskoch.movies.features.films.functions.fullPosterUrl
import com.extensions.android.components.ui.transitions.TextTransition
import com.extensions.android.functions.withTransition

class FilmViewHolder(
    itemView: View,
    private val getListener: () -> FilmsAdapter.ActionListener?
) : BaseViewHolder<Film>(itemView) {

    private val viewBinding = ListItemFilmBinding.bind(itemView)

    init {
        viewBinding.favouriteButton.setOnClickListener(this::onFavouriteButtonClicked)
        viewBinding.shareButton.setOnClickListener(this::onShareButtonClicked)
    }

    override fun onBindView(item: Film, payload: MutableList<Any>?) {

        fun shouldUpdate(@IdRes viewId: Int): Boolean {
            return payload.isNullOrEmpty() || payload.any { (it as Set<*>).contains(viewId) }
        }

        with(viewBinding) {
            if (shouldUpdate(R.id.posterImageView)) {
                initPosterImage()
            }

            if (shouldUpdate(R.id.titleTextView)) {
                titleTextView.text = item.title
            }

            if (shouldUpdate(R.id.overviewTextView)) {
                overviewTextView.text = item.overview
            }

            if (shouldUpdate(R.id.favouriteButton)) {
                initFavouriteButton()
            }
        }
    }

    private fun initPosterImage() {
        if (item.posterPath != null) {
            loadImage()
        } else {
            showImagePlaceholder()
        }
    }

    private fun loadImage() {
        Glide.with(viewBinding.posterImageView)
            .setDefaultRequestOptions(
                RequestOptions()
                    .error(R.mipmap.ic_launcher_round)
                    .placeholder(R.mipmap.ic_launcher_round)
            )
            .load(fullPosterUrl(item.posterPath))
            .into(viewBinding.posterImageView)
    }

    private fun showImagePlaceholder() {
        Glide.with(viewBinding.posterImageView)
            .load(R.mipmap.ic_launcher_round)
            .into(viewBinding.posterImageView)
    }

    private fun initFavouriteButton() {
        with(viewBinding) {
            val transition = TransitionSet().apply {
                duration = root.context.SHORT_ANIMATION_DURATION
                addTransition(ChangeBounds().apply { addTarget(Button::class.java) })
                addTransition(TextTransition().apply { addTarget(favouriteButton) })
            }

            withTransition(contentLayout, transition) {
                favouriteButton.setText(
                    if (item.isFavourite) {
                        R.string.remove_from_favourites
                    } else {
                        R.string.add_to_favourites
                    }
                )
            }
        }
    }

    private fun onFavouriteButtonClicked(view: View) {
        getListener()?.onFavouriteClicked(item)
    }

    private fun onShareButtonClicked(view: View) {
        getListener()?.onShareClicked(item)
    }
}