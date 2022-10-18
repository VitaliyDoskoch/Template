package com.doskoch.template.anime.useCase

import com.doskoch.template.anime.toDbAnime
import com.doskoch.template.api.jikan.services.responses.GetTopAnimeResponse
import com.doskoch.template.core.components.paging.SimpleInMemoryStorage
import com.doskoch.template.database.schema.anime.DbAnimeDao

class SaveAnimeToFavoriteUseCase(
    private val storage: SimpleInMemoryStorage<Int, GetTopAnimeResponse.Data>,
    private val dbAnimeDao: DbAnimeDao
) {
    suspend fun invoke(id: Int) {
        val item = storage.pages.values.filterNotNull().flatten().first { it.malId == id }
        dbAnimeDao.insert(item.toDbAnime())
    }
}