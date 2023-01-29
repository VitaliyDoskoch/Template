package com.doskoch.template.anime.presentation.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.doskoch.template.anime.presentation.common.uiModel.AnimeUiModel
import com.doskoch.template.anime.presentation.common.uiModel.toUiModel
import com.doskoch.template.anime.presentation.common.useCase.DeleteAnimeFromFavoriteUseCase
import com.doskoch.template.anime.presentation.navigation.AnimeFeatureNavigator
import com.doskoch.template.core.android.components.error.CoreError
import com.doskoch.template.core.android.components.error.ErrorMapper
import com.doskoch.template.core.android.components.error.GlobalErrorHandler
import com.doskoch.template.core.android.ext.launchAction
import com.doskoch.template.core.kotlin.di.FeatureScoped
import com.doskoch.template.database.schema.anime.DbAnime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FavoriteAnimeViewModel @Inject constructor(
    @FeatureScoped
    private val navigator: AnimeFeatureNavigator,
    private val pager: Pager<Int, DbAnime>,
    private val deleteAnimeFromFavoriteUseCase: DeleteAnimeFromFavoriteUseCase,
    private val globalErrorHandler: GlobalErrorHandler,
    private val errorMapper: ErrorMapper
) : ViewModel() {

    private val _state = MutableStateFlow(
        FavoriteAnimeState(
            pagingFlow = emptyFlow(),
            actions = FavoriteAnimeState.Actions(
                onBackClick = this::onBackClick,
                onItemClick = this::onItemClick,
                onItemFavoriteClick = this::onItemFavoriteClick
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

    private fun onBackClick() = navigator.navigateUp()

    private fun onItemClick(item: AnimeUiModel) = navigator.toDetails(item.id, item.title)

    private fun onItemFavoriteClick(item: AnimeUiModel) = launchAction(
        action = { deleteAnimeFromFavoriteUseCase.invoke(item.id) },
        onError = { globalErrorHandler.handle(errorMapper.toCoreError(it, CoreError.FailedToSaveChanges)) }
    )
}
