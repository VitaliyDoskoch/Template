package com.doskoch.template.anime.presentation.common.useCase

import com.doskoch.template.database.schema.anime.DbAnimeDao
import javax.inject.Inject

class DeleteAnimeFromFavoriteUseCase @Inject constructor(private val dbAnimeDao: DbAnimeDao) {
    suspend fun invoke(id: Int) = dbAnimeDao.delete(id)
}
