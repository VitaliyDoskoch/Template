package com.doskoch.template.anime.screens.favorite

import androidx.paging.PagingData
import com.doskoch.template.anime.uiModel.AnimeUiModel
import kotlinx.coroutines.flow.Flow

data class FavoriteAnimeState(
    val pagingFlow: Flow<PagingData<AnimeUiModel>>,
    val actions: Actions
) {
    data class Actions(
        val onBackClick: () -> Unit,
        val onItemClick: (AnimeUiModel) -> Unit,
        val onItemFavoriteClick: (AnimeUiModel) -> Unit
    )
}