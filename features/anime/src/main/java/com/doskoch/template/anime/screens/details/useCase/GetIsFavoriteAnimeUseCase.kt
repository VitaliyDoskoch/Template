package com.doskoch.template.anime.screens.details.useCase

import com.doskoch.template.database.schema.anime.DbAnimeDao

class GetIsFavoriteAnimeUseCase(private val dbAnimeDao: DbAnimeDao) {
    fun invoke(id: Int) = dbAnimeDao.isFavorite(id)
}