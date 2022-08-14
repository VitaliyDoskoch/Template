package com.doskoch.template.anime.screens.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.doskoch.template.anime.data.AnimeItem

class TopAnimeViewModel(
    private val pager: Pager<Int, AnimeItem>
) : ViewModel() {

    val items = pager.flow.cachedIn(viewModelScope)

}