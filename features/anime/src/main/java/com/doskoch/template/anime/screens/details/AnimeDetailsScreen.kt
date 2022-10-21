package com.doskoch.template.anime.screens.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.ExperimentalPagingApi
import com.doskoch.template.anime.R
import com.doskoch.template.anime.di.Module
import com.doskoch.template.anime.screens.favorite.FavoriteAnimeState
import com.doskoch.template.core.components.theme.Dimensions
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalPagingApi::class)
@Composable
fun AnimeDetailsScreen(
    animeId: Int,
    title: String,
    vm: AnimeDetailsViewModel = viewModel { Module.animeDetailsViewModel(animeId, title) }
) {
    val state = vm.state.collectAsState().value

    Scaffold(
        topBar = {
            TopBar(state = state)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

        }
    }
}

@Composable
private fun TopBar(state: AnimeDetailsState) {
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
                text = state.title,
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
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