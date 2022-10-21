package com.doskoch.template.anime.screens.top.useCase

import com.doskoch.template.database.schema.anime.DbAnimeDao

class GetFavoriteAnimeIdsUseCase(private val dbAnimeDao: DbAnimeDao) {
    fun invoke() = dbAnimeDao.ids()
}
