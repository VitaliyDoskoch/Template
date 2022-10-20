package com.doskoch.template.anime.screens.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.doskoch.template.anime.R
import com.doskoch.template.anime.di.Module
import com.doskoch.template.anime.ui.AnimeItem
import com.doskoch.template.core.components.error.toCoreError
import com.doskoch.template.core.components.theme.Dimensions
import com.doskoch.template.core.ui.modifier.simpleVerticalScrollbar
import com.doskoch.template.core.ui.paging.LazyPagingColumn
import com.doskoch.template.core.ui.paging.LoadStateItem
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalPagingApi::class)
@Composable
fun FavoriteAnimeScreen(vm: FavoriteAnimeViewModel = viewModel { Module.favoriteAnimeViewModel() }) {
    val state = vm.state.collectAsState().value

    Scaffold(
        topBar = {
            TopBar(state = state)
        }
    ) { paddingValues ->
        val items = state.pagingFlow.collectAsLazyPagingItems()
        val lazyListState = rememberLazyListState()

        LazyPagingColumn(
            itemCount = items.itemCount,
            loadStates = items.loadState,
            modifier = Modifier
                .padding(paddingValues)
                .navigationBarsPadding()
                .fillMaxSize()
                .simpleVerticalScrollbar(lazyListState),
            state = lazyListState
        ) {
            itemsIndexed(items) { position, item ->
                item?.let {
                    AnimeItem(
                        item = item,
                        position = position,
                        onFavoriteClick = { state.actions.onItemFavoriteClick(item) },
                        modifier = Modifier
                            .clickable { state.actions.onItemClick(item) }
                    )
                }
            }
        }
    }
}

@Composable
private fun TopBar(state: FavoriteAnimeState) {
    val systemUiController = rememberSystemUiController()
    val statusBarColor = MaterialTheme.colors.primary

    SideEffect {
        systemUiController.setStatusBarColor(statusBarColor)
    }

    TopAppBar(
        modifier = Modifier
            .statusBarsPadding(),
        title = {
            Text(
                text = stringResource(R.string.favorite_toolbar_title),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onPrimary
            )
        },
        navigationIcon = {
            IconButton(
                onClick = state.actions.onBackClick
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = stringResource(R.string.desc_navigate_back),
                    modifier = Modifier
                        .size(Dimensions.icon_24)
                )
            }
        }
    )
}