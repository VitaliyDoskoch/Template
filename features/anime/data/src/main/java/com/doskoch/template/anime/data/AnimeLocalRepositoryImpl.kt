package com.doskoch.template.anime.data

import com.doskoch.template.anime.domain.AnimeLocalRepository
import com.doskoch.template.database.schema.anime.DbAnimeDao
import javax.inject.Inject

class AnimeLocalRepositoryImpl @Inject constructor(
    private val dbAnimeDao: DbAnimeDao
) : AnimeLocalRepository {
    override suspend fun deleteAnime(id: Int) = dbAnimeDao.delete(id)
    override fun isFavorite(id: Int) = dbAnimeDao.isFavorite(id)
}