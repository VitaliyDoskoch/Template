package com.doskoch.movies.database.schema.films.entity

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.doskoch.movies.database.AppDatabase
import com.doskoch.movies.database.schema.films.DbFilm
import com.doskoch.movies.database.schema.films.DbFilmDao
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4ClassRunner::class)
class DbFilmDaoTest {

    lateinit var database: AppDatabase

    val dao: DbFilmDao
        get() = database.dbFilmDao()

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

    @Test
    @DisplayName("clear() deletes all items")
    fun clear() {
        val items = testSuite(5)

        dao.insert(items)
        dao.clear()

        assertEquals(emptyList(), dao.select())
        assertEquals(0, dao.count())
    }

    private fun testSuite(count: Int): List<DbFilm> {
        return mutableListOf<DbFilm>()
            .apply {
                for (id in 0L until count) {
                    add(create(id))
                }
            }
            .sortedByDescending { it.releaseDate }
    }

    private fun create(id: Long): DbFilm {
        return DbFilm(
            id = id,
            posterPath = if (id % 2 == 0L) "posterPath" else null,
            title = "Film $id",
            overview = "overview $id",
            releaseDate = id * 1_000
        )
    }
}