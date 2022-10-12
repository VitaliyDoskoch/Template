package com.doskoch.template.anime.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import com.doskoch.template.anime.data.AnimeItem
import com.doskoch.template.anime.navigation.AnimeFeatureNavigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow

class FavoriteAnimeViewModel(
    private val navigator: AnimeFeatureNavigator
) : ViewModel() {

    private val _state = MutableStateFlow(initialState())
    val state = _state.asStateFlow()

    private fun initialState(): FavoriteAnimeState = FavoriteAnimeState(
        pagingData = emptyFlow(),
        actions = FavoriteAnimeState.Actions(
            onBackClick = this::onBackClick,
            onItemClick = this::onItemClick
        )
    )

    private fun onBackClick() {
        navigator.navigateUp()
    }

    private fun onItemClick(item: AnimeItem) {

    }
}