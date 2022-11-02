package com.doskoch.template.anime.common.useCase

import com.doskoch.template.database.schema.anime.DbAnimeDao

class DeleteAnimeFromFavoriteUseCase(private val dbAnimeDao: DbAnimeDao) {
    suspend fun invoke(id: Int) = dbAnimeDao.delete(id)
}
