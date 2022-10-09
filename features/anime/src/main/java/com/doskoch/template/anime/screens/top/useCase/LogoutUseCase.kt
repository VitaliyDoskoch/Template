package com.doskoch.template.anime.screens.top.useCase

import com.doskoch.template.anime.di.AnimeFeatureRepository
import kotlinx.coroutines.withContext

class LogoutUseCase(
    private val repository: AnimeFeatureRepository
) {
    suspend fun invoke() = repository.logout()
}