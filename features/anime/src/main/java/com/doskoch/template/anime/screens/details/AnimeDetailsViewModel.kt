package com.doskoch.template.anime.screens.details

import androidx.lifecycle.ViewModel
import com.doskoch.template.anime.navigation.AnimeFeatureNavigator
import com.doskoch.template.anime.screens.details.AnimeDetailsState.ScreenState
import com.doskoch.template.anime.screens.details.useCase.LoadAnimeDetailsUseCase
import com.doskoch.template.core.components.error.CoreError
import com.doskoch.template.core.components.error.toCoreError
import com.doskoch.template.core.functions.launchAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AnimeDetailsViewModel(
    private val animeId: Int,
    private val title: String,
    private val navigator: AnimeFeatureNavigator,
    private val loadAnimeDetailsUseCase: LoadAnimeDetailsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(
        AnimeDetailsState(
            title = title,
            isFavorite = false,
            screenState = ScreenState.Loading,
            actions = AnimeDetailsState.Actions(
                onBackClick = this::onBackClick
            )
        )
    )
    val state = _state.asStateFlow()

    init {
        loadAnimeDetails()
    }

    private fun loadAnimeDetails() = launchAction(
        action = {
            _state.update { it.copy(screenState = ScreenState.Loading) }
            _state.update { it.copy(screenState = loadAnimeDetailsUseCase.invoke(animeId).data.toContent()) }
        },
        onError = { error ->
            _state.update { it.copy(screenState = ScreenState.Error(error.toCoreError(CoreError.FailedToLoadData()))) }
        }
    )

    private fun onBackClick() = navigator.navigateUp()
}