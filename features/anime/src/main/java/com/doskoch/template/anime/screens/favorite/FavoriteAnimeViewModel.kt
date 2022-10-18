package com.doskoch.template.anime.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.doskoch.template.anime.navigation.AnimeFeatureNavigator
import com.doskoch.template.anime.uiModel.AnimeUiModel
import com.doskoch.template.anime.uiModel.toUiModel
import com.doskoch.template.database.schema.anime.DbAnime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FavoriteAnimeViewModel(
    private val navigator: AnimeFeatureNavigator,
    private val pager: Pager<Int, DbAnime>
) : ViewModel() {

    private val _state = MutableStateFlow(
        FavoriteAnimeState(
            pagingFlow = emptyFlow(),
            actions = FavoriteAnimeState.Actions(
                onBackClick = this::onBackClick,
                onItemClick = this::onItemClick
            )
        )
    )
    val state = _state.asStateFlow()

    private val pagingFlow = pager.flow
        .map { it.map(DbAnime::toUiModel) }
        .cachedIn(viewModelScope)

    init {
        _state.update { it.copy(pagingFlow = pagingFlow) }
    }

    private fun onBackClick() {
        navigator.navigateUp()
    }

    private fun onItemClick(item: AnimeUiModel) {

    }
}