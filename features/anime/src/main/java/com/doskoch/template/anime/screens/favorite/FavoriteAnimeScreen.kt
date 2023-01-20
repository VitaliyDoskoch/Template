package com.doskoch.template.anime.screens.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.doskoch.template.anime.R
import com.doskoch.template.anime.common.ui.AnimeItem
import com.doskoch.template.anime.di._Module
import com.doskoch.template.core.components.theme.Dimensions
import com.doskoch.template.core.ui.modifier.simpleVerticalScrollbar
import com.doskoch.template.core.ui.paging.PagingScaffold
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalPagingApi::class)
@Composable
fun FavoriteAnimeScreen(vm: FavoriteAnimeViewModel = viewModel { _Module.favoriteAnimeViewModel() }) {
    val state = vm.state.collectAsState().value

    Scaffold(
        topBar = {
            TopBar(state = state)
        }
    ) { paddingValues ->
        val items = state.pagingFlow.collectAsLazyPagingItems()

        PagingScaffold(
            itemCount = items.itemCount,
            loadState = items.loadState,
            modifier = Modifier
                .fillMaxSize()
        ) {
            val lazyListState = rememberLazyListState()

            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .navigationBarsPadding()
                    .fillMaxSize()
                    .simpleVerticalScrollbar(lazyListState),
                state = lazyListState
            ) {
                itemsIndexed(
                    items = items,
                    key = { _, item -> item.id }
                ) { position, item ->
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
                text = stringResource(R.string.favorite_anime_screen_toolbar_title),
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
                    contentDescription = stringResource(R.string.common_desc_navigate_back),
                    modifier = Modifier
                        .size(Dimensions.icon_24)
                )
            }
        }
    )
}
