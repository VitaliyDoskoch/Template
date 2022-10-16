package com.doskoch.template.anime.screens.top

import androidx.paging.PagingData
import com.doskoch.template.anime.screens.top.uiModel.AnimeTypeUiModel
import com.doskoch.template.anime.uiModel.AnimeUiModel
import kotlinx.coroutines.flow.Flow

data class TopAnimeState(
    val animeType: AnimeTypeUiModel,
    val showAnimeTypeMenu: Boolean,
    val showLogoutDialog: Boolean,
    val pagingFlow: Flow<PagingData<AnimeUiModel>>,
    val actions: Actions
) {
    data class Actions(
        val onAnimeTypeClick: () -> Unit,
        val onDismissAnimeTypeMenu: () -> Unit,
        val onUpdateAnimeType: (AnimeTypeUiModel) -> Unit,
        val onFavoriteClick: () -> Unit,
        val onLogoutClick: () -> Unit,
        val onDismissLogoutDialog: () -> Unit,
        val onConfirmLogoutClick: () -> Unit,
        val onItemClick: (AnimeUiModel) -> Unit
    )
}
