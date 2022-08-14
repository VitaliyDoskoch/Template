package com.doskoch.template.anime.screens.top

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.doskoch.template.anime.data.AnimeItem
import com.doskoch.template.anime.data.AnimeType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TopAnimeViewModel(
    private val pager: Pager<Int, AnimeItem>
) : ViewModel() {

    private val _state = MutableStateFlow(State.default(this))
    val state = _state.asStateFlow()

    private fun onAnimeTypeClick() {

    }

    private fun onUpdateAnimeType(type: AnimeType) {
        _state.update { it.copy(animeType = type) }
    }

    data class State(
        val animeType: AnimeType,
        val pagingData: Flow<PagingData<AnimeItem>>,
        val actions: Actions
    ) {

        data class Actions(
            val onAnimeTypeClick: () -> Unit,
            val onUpdateAnimeType: (AnimeType) -> Unit
        )

        companion object {
            fun default(vm: TopAnimeViewModel) = State(
                animeType = AnimeType.Tv,
                pagingData = vm.pager.flow.cachedIn(vm.viewModelScope),
                actions = Actions(
                    onAnimeTypeClick = vm::onAnimeTypeClick,
                    onUpdateAnimeType = vm::onUpdateAnimeType
                )
            )
        }
    }
}