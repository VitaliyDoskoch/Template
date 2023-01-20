package com.doskoch.template.anime.screens.details.useCase

import com.doskoch.template.database.schema.anime.DbAnimeDao
import javax.inject.Inject

class GetIsFavoriteAnimeUseCase @Inject constructor(private val dbAnimeDao: DbAnimeDao) {
    fun invoke(id: Int) = dbAnimeDao.isFavorite(id)
}
