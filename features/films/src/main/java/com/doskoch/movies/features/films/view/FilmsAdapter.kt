package com.doskoch.movies.features.films.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.doskoch.movies.core.components.ui.base.recyclerView.paging.BasePagedRecyclerAdapter
import com.doskoch.movies.core.components.ui.base.recyclerView.paging.fetchState.FetchState
import com.doskoch.movies.database.modules.films.view.Film
import com.doskoch.movies.features.films.R

class FilmsAdapter : BasePagedRecyclerAdapter<FetchState, Film, FilmViewHolder>() {

    interface ActionListener {
        fun onFavouriteClicked(item: Film)
        fun onShareClicked(item: Film)
    }

    var actionListener: ActionListener? = null

    override val itemCallback = object : DiffUtil.ItemCallback<Film>() {
        override fun areItemsTheSame(old: Film, new: Film): Boolean {
            return old.id == new.id
        }

        override fun areContentsTheSame(old: Film, new: Film): Boolean {
            return old == new
        }

        override fun getChangePayload(old: Film, new: Film): Any? {
            return mutableSetOf<Int>().apply {
                if (old.isFavourite != new.isFavourite) add(R.id.favouriteButton)
                if (isEmpty()) return null
            }
        }
    }

    override fun onCreateRegularViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        return FilmViewHolder(
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_film, parent, false),
            getListener = { actionListener }
        )
    }

    override fun onBindRegularViewHolder(holder: FilmViewHolder,
                                         position: Int,
                                         payloads: MutableList<Any>?) {
        asyncPagedListDiffer.getItem(position)?.let { holder.bindView(it, payloads) }
    }
}