package com.doskoch.template.anime.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.doskoch.template.anime.INITIAL_LOAD_SIZE
import com.doskoch.template.anime.PAGE_SIZE
import com.doskoch.template.anime.data.AnimeItem
import com.doskoch.template.anime.di.Injector
import com.doskoch.template.anime.navigation.AnimeFeatureNavigator
import com.doskoch.template.database.schema.anime.DbAnime
import com.doskoch.template.database.schema.anime.DbAnimeDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

class FavoriteAnimeViewModel(
    private val navigator: AnimeFeatureNavigator,
    private val remoteMediator: FavoriteAnimeRemoteMediator,
    private val dao: DbAnimeDao,
    private val converter: Converter
) : ViewModel() {

    private val _state = MutableStateFlow(initialState())
    val state = _state.asStateFlow()

    @OptIn(ExperimentalPagingApi::class)
    private fun initialState(): FavoriteAnimeState = FavoriteAnimeState(
        pagingData = Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, initialLoadSize = INITIAL_LOAD_SIZE, enablePlaceholders = true),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { dao.pagingSource() }
        )
            .flow
            .map { it.map(converter::toAnimeItem) }
            .cachedIn(viewModelScope),
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