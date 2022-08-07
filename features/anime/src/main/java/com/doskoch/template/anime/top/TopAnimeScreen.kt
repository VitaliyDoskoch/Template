package com.doskoch.template.anime.top

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.size.Dimension
import com.doskoch.legacy.android.viewModel.viewModelFactory
import com.doskoch.template.anime.R
import com.doskoch.template.anime.data.AnimeItem
import com.doskoch.template.anime.di.Module
import com.doskoch.template.core.theme.Dimensions
import com.doskoch.template.core.ui.CoreTopAppBar

@Composable
fun TopAnimeScreen() {
    TopAnimeScreen(
        vm = viewModel(factory = viewModelFactory { Module.topAnimeViewModel })
    )
}

@Composable
private fun TopAnimeScreen(vm: TopAnimeViewModel) {
//    TopAnimeScreen(
//        items = emp
//    )
}

@Composable
private fun TopAnimeScreen(
    items: LazyPagingItems<AnimeItem>
) {
    Scaffold(
        topBar = {
            TopAppBar {

            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .navigationBarsPadding()
                .fillMaxSize()
        ) {

        }
    }
}

@Composable
private fun AnimeItem(item: AnimeItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = item.smallImageUrl,
            contentDescription = stringResource(R.string.desc_anime_image),
            modifier = Modifier
                .padding(start = Dimensions.space_16, top = Dimensions.space_16, bottom = Dimensions.space_16)
                .size(120.dp),
            placeholder = painterResource(R.drawable.ic_photo),
            error = painterResource(R.drawable.ic_sync_problem)
        )

        Column(
            modifier = Modifier
                .padding(Dimensions.space_16)
                .weight(1f)
        ) {
            Text(
                text = item.title
            )
        }
    }
}