package com.doskoch.template.anime.screens.favorite

import androidx.paging.PagingData
import com.doskoch.template.anime.data.AnimeItem
import kotlinx.coroutines.flow.Flow

data class FavoriteAnimeState(
    val pagingData: Flow<PagingData<AnimeItem>>,
    val actions: Actions
) {
    data class Actions(
        val onBackClick: () -> Unit,
        val onItemClick: (AnimeItem) -> Unit
    )
}