package com.doskoch.template.anime.screens.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.ExperimentalPagingApi
import com.doskoch.legacy.android.viewModel.typedViewModelFactory
import com.doskoch.template.anime.di.Module

@OptIn(ExperimentalPagingApi::class)
@Composable
fun AnimeDetailsScreen() {
    AnimeDetailsScreen(
        vm = viewModel(factory = typedViewModelFactory { Module.animeDetailsViewModel })
    )
}

@Composable
private fun AnimeDetailsScreen(vm: AnimeDetailsViewModel) {
    Scaffold {
        Box(Modifier.padding(it))
    }
}