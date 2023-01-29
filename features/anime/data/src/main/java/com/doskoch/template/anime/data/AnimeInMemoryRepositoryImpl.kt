package com.doskoch.template.anime.data

import com.doskoch.template.anime.domain.AnimeInMemoryRepository
import com.doskoch.template.anime.domain.model.AnimeItem
import com.doskoch.template.core.android.components.paging.SimpleInMemoryStorage
import com.doskoch.template.core.kotlin.di.FeatureScoped
import javax.inject.Inject

class AnimeInMemoryRepositoryImpl @Inject constructor(
    @FeatureScoped
    private val storage: SimpleInMemoryStorage<Int, AnimeItem>
) : AnimeInMemoryRepository {

    override fun storeAnime(clearExistingData: Boolean, previousKey: Int?, currentKey: Int, nextKey: Int?, page: List<AnimeItem>) {
        storage.inTransaction {
            if (clearExistingData) {
                clear()
            }

            store(previousKey, currentKey, nextKey, page)
        }
    }

    override fun clearStorage() = storage.inTransaction { clear() }

    override fun lastPagingKey() = storage.pages.value.keys.lastOrNull()

    override fun anime(id: Int) = storage.pages.value.values.filterNotNull().flatten().first { it.id == id }
}
