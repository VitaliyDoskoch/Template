package com.doskoch.movies.database.schema.films.entity

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.doskoch.movies.database.AppDatabase
import com.doskoch.movies.database.schema.films.DbFavouriteFilm
import com.doskoch.movies.database.schema.films.DbFavouriteFilmDao
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4ClassRunner::class)
class DbFavouriteFilmDaoTest {

    lateinit var database: AppDatabase

    val dao: DbFavouriteFilmDao
        get() = database.dbFavouriteFilmDao()

    @BeforeEach
    fun beforeEach() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .build()
    }

    @AfterEach
    fun afterEach() {
        database.close()
    }

    @ParameterizedTest(name = "count = {0}")
    @ValueSource(ints = [0, 5])
    @DisplayName("select() returns all items")
    fun select(count: Int) {
        val items = testSuite(count)

        dao.insert(items)

        assertEquals(items, dao.select())
    }

    @ParameterizedTest(name = "count = {0}")
    @ValueSource(ints = [0, 5])
    @DisplayName("count() returns actual value")
    fun count(count: Int) {
        val items = testSuite(count)

        dao.insert(items)

        assertEquals(count, dao.count())
    }

    @ParameterizedTest(name = "id = {0}")
    @ValueSource(strings = ["-1,0", "2,4", "1,2,3,4,5"])
    @DisplayName("delete(id: Long) deletes only item with specified id")
    fun delete(ids: String) {
        val idsToDelete = ids.split(",").map(String::toLong)
        val items = testSuite(5)

        dao.insert(items)
        idsToDelete.forEach { dao.delete(it) }

        assertEquals(items.filter { it.id !in idsToDelete }, dao.select())
    }

    private fun testSuite(count: Int): List<DbFavouriteFilm> {
        return mutableListOf<DbFavouriteFilm>()
            .apply {
                for (id in 0L until count) {
                    add(create(id))
                }
            }
            .sortedByDescending { it.releaseDate }
    }

    private fun create(id: Long): DbFavouriteFilm {
        return DbFavouriteFilm(
            id = id,
            posterPath = if (id % 2 == 0L) "posterPath" else null,
            title = "Film $id",
            overview = "overview $id",
            releaseDate = id * 1_000
        )
    }
}
