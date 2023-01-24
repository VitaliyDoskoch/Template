package com.doskoch.template.anime.presentation.screens.top.useCase

import com.doskoch.template.database.schema.anime.DbAnimeDao
import javax.inject.Inject

class GetFavoriteAnimeIdsUseCase @Inject constructor(private val dbAnimeDao: DbAnimeDao) {
    fun invoke() = dbAnimeDao.ids()
}
