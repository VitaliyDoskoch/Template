package com.doskoch.template.anime.common.useCase

import com.doskoch.template.anime.common.toDbAnime
import com.doskoch.template.api.jikan.services.top.responses.GetTopAnimeResponse
import com.doskoch.template.core.components.paging.SimpleInMemoryStorage
import com.doskoch.template.database.schema.anime.DbAnimeDao
import javax.inject.Inject

class SaveAnimeToFavoriteUseCase @Inject constructor(
    private val storage: SimpleInMemoryStorage<Int, GetTopAnimeResponse.Data>,
    private val dbAnimeDao: DbAnimeDao
) {
    suspend fun invoke(id: Int) {
        val item = storage.pages.value.values.filterNotNull().flatten().first { it.malId == id }
        dbAnimeDao.insert(item.toDbAnime())
    }
}
