package com.doskoch.template.anime.screens.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.ExperimentalPagingApi
import com.doskoch.template.anime.di.Module

@OptIn(ExperimentalPagingApi::class)
@Composable
fun AnimeDetailsScreen(vm: AnimeDetailsViewModel = viewModel { Module.animeDetailsViewModel() }) {
    Scaffold {
        Box(Modifier.padding(it))
    }
}