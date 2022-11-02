package com.doskoch.template.anime.screens.details

import androidx.lifecycle.ViewModel
import com.doskoch.template.anime.navigation.AnimeFeatureNavigator
import com.doskoch.template.anime.screens.details.AnimeDetailsState.ScreenState
import com.doskoch.template.anime.screens.details.useCase.GetIsFavoriteAnimeUseCase
import com.doskoch.template.anime.screens.details.useCase.LoadAnimeDetailsUseCase
import com.doskoch.template.anime.useCase.DeleteAnimeFromFavoriteUseCase
import com.doskoch.template.anime.useCase.SaveAnimeToFavoriteUseCase
import com.doskoch.template.core.components.error.CoreError
import com.doskoch.template.core.components.error.GlobalErrorHandler
import com.doskoch.template.core.components.error.toCoreError
import com.doskoch.template.core.ext.launchAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update

class AnimeDetailsViewModel(
    private val animeId: Int,
    private val title: String,
    private val navigator: AnimeFeatureNavigator,
    private val globalErrorHandler: GlobalErrorHandler,
    private val getIsFavoriteAnimeUseCase: GetIsFavoriteAnimeUseCase,
    private val loadAnimeDetailsUseCase: LoadAnimeDetailsUseCase,
    private val saveAnimeToFavoriteUseCase: SaveAnimeToFavoriteUseCase,
    private val deleteAnimeFromFavoriteUseCase: DeleteAnimeFromFavoriteUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<AnimeDetailsState> = MutableStateFlow(
        AnimeDetailsState(
            title = title,
            isFavorite = false,
            screenState = ScreenState.Loading,
            actions = AnimeDetailsState.Actions(
                onBackClick = this::onBackClick,
                onFavoriteClick = this::onFavoriteClick
            )
        )
    )
    val state = _state.asStateFlow()

    init {
        observeIsFavorite()
        loadAnimeDetails()
    }

    private fun observeIsFavorite() = launchAction(
        action = {
            getIsFavoriteAnimeUseCase.invoke(animeId).collectLatest { isFavorite ->
                _state.update { it.copy(isFavorite = isFavorite) }
            }
        },
        onError = { error ->
            _state.update { it.copy(screenState = ScreenState.Error(error.toCoreError(CoreError.FailedToLoadData()))) }
        }
    )

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

    private fun onFavoriteClick() = launchAction(
        action = {
            if (state.value.isFavorite) {
                deleteAnimeFromFavoriteUseCase.invoke(animeId)
            } else {
                saveAnimeToFavoriteUseCase.invoke(animeId)
            }
        },
        onError = { globalErrorHandler.handle(it.toCoreError(CoreError.FailedToSaveChanges())) }
    )
}
