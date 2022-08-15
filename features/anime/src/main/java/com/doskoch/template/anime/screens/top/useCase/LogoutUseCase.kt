package com.doskoch.template.anime.screens.top.useCase

import com.doskoch.template.anime.di.AnimeFeatureGlobalNavigator
import com.doskoch.template.anime.di.AnimeFeatureRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LogoutUseCase(
    private val repository: AnimeFeatureRepository,
    private val globalNavigator: AnimeFeatureGlobalNavigator,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun invoke() = withContext(dispatcher) {
        repository.logout()
        globalNavigator.toSplash()
    }
}