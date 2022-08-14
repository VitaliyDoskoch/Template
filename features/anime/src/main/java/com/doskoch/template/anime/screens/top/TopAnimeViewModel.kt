package com.doskoch.template.anime.screens.top

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.doskoch.template.anime.data.AnimeItem
import com.doskoch.template.anime.data.AnimeType
import com.doskoch.template.core.components.paging.SimpleInMemoryStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update

class TopAnimeViewModel(
    private val pagerFactory: PagerFactory,
    private val storage: SimpleInMemoryStorage<Int, AnimeItem>
) : ViewModel() {

    private val _state = MutableStateFlow(State.default(this))
    val state = _state.asStateFlow()

    private fun onAnimeTypeClick() {
        _state.update { it.copy(showAnimeTypeMenu = true) }
    }

    private fun onDismissAnimeTypeMenu() {
        _state.update { it.copy(showAnimeTypeMenu = false) }
    }

    private fun onUpdateAnimeType(type: AnimeType) {
        storage.clear()

        _state.update {
            it.copy(
                animeType = type,
                showAnimeTypeMenu = false,
                pagingData = pagerFactory.create(type).flow.cachedIn(viewModelScope)
            )
        }
    }

    fun interface PagerFactory {
        fun create(animeType: AnimeType): Pager<Int, AnimeItem>
    }

    data class State(
        val animeType: AnimeType,
        val showAnimeTypeMenu: Boolean,
        val pagingData: Flow<PagingData<AnimeItem>>,
        val actions: Actions
    ) {

        data class Actions(
            val onAnimeTypeClick: () -> Unit,
            val onDismissAnimeTypeMenu: () -> Unit,
            val onUpdateAnimeType: (AnimeType) -> Unit
        )

        companion object {
            fun default(vm: TopAnimeViewModel) = State(
                animeType = AnimeType.Tv,
                showAnimeTypeMenu = false,
                pagingData = vm.pagerFactory.create(AnimeType.Tv).flow.cachedIn(vm.viewModelScope),
                actions = Actions(
                    onAnimeTypeClick = vm::onAnimeTypeClick,
                    onDismissAnimeTypeMenu = vm::onDismissAnimeTypeMenu,
                    onUpdateAnimeType = vm::onUpdateAnimeType
                )
            )
        }
    }
}