package com.doskoch.movies.database.modules.films.view

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.doskoch.movies.database.AppDatabase
import com.doskoch.movies.database.modules.films.entity.DbFavouriteFilm
import com.doskoch.movies.database.modules.films.entity.DbFavouriteFilmDao
import com.doskoch.movies.database.modules.films.entity.DbFilm
import com.doskoch.movies.database.modules.films.entity.DbFilmDao
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4ClassRunner::class)
class FilmDaoTest {

    lateinit var database: AppDatabase

    val dbFilmDao: DbFilmDao
        get() = database.dbFilmDao()

    val dbFavouriteFilmDao: DbFavouriteFilmDao
        get() = database.dbFavouriteFilmDao()

    val filmDao: FilmDao
        get() = database.filmDao()

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

    @Nested
    @DisplayName("selectAll(limit: Int, offset: Int) works as expected")
    inner class SelectAll {

        @ParameterizedTest(name = "DbFilm count = {0}, DbFavouriteFilm count = {1}")
        @CsvSource(value = ["0, 0", "1, 2", "2, 2", "2, 1"])
        @DisplayName("selectAll(limit = 5, offset = 0) returns actual value")
        fun selectAll_actual(dbFilmCount: Int, dbFavouriteFilmCount: Int) {
            val dbFilms = dbFilmTestSuite(dbFilmCount)
            val dbFavouriteFilms = dbFavouriteFilmTestSuite(dbFavouriteFilmCount)

            dbFilmDao.insert(dbFilms)
            dbFavouriteFilmDao.insert(dbFavouriteFilms)

            assertEquals(dbFilms.toFilms(dbFavouriteFilms), filmDao.selectAll(5, 0))
        }

        @ParameterizedTest(name = "limit = {0}, offset = {1}")
        @CsvSource(value = ["0, 0", "2, 0", "2, 2", "0, 2"])
        @DisplayName("selectAll(limit: Int, offset: Int) returns actual value. Test suite size = 5")
        fun selectAll_limitOffset(limit: Int, offset: Int) {
            val dbFilms = dbFilmTestSuite(5)

            dbFilmDao.insert(dbFilms)

            val expected = dbFilms
                .filterIndexed { index, _ -> index >= offset }
                .take(limit)
                .toFilms(emptyList())

            assertEquals(expected, filmDao.selectAll(limit, offset))
        }
    }

    @Nested
    @DisplayName("selectFavourite(limit: Int, offset: Int) works as expected")
    inner class SelectFavourite {

        @ParameterizedTest(name = "DbFavouriteFilm count = {0}")
        @ValueSource(ints = [0, 5])
        @DisplayName("selectFavourite(limit = 5, offset = 0) returns actual value")
        fun selectFavourite_actual(count: Int) {
            val dbFavouriteFilms = dbFavouriteFilmTestSuite(count)

            dbFavouriteFilmDao.insert(dbFavouriteFilms)

            assertEquals(dbFavouriteFilms.toFilms(), filmDao.selectFavourite(5, 0))
        }

        @ParameterizedTest(name = "limit = {0}, offset = {1}")
        @CsvSource(value = ["0, 0", "2, 0", "2, 2", "0, 2"])
        @DisplayName(
            "selectFavourite(limit: Int, offset: Int) returns actual value. Test suite size = 5"
        )
        fun selectFavourite_limitOffset(limit: Int, offset: Int) {
            val dbFavouriteFilms = dbFavouriteFilmTestSuite(5)

            dbFavouriteFilmDao.insert(dbFavouriteFilms)

            val expected = dbFavouriteFilms
                .filterIndexed { index, _ -> index >= offset }
                .take(limit)
                .toFilms()

            assertEquals(expected, filmDao.selectFavourite(limit, offset))
        }
    }

    private fun dbFilmTestSuite(count: Int): List<DbFilm> {
        return mutableListOf<DbFilm>()
            .apply {
                for (id in 0L until count) {
                    add(createDbFilm(id))
                }
            }
            .sortedByDescending { it.releaseDate }
    }

    private fun createDbFilm(id: Long): DbFilm {
        return DbFilm(
            id = id,
            posterPath = if (id % 2 == 0L) "posterPath" else null,
            title = "Film $id",
            overview = "overview $id",
            releaseDate = id * 1_000
        )
    }

    private fun dbFavouriteFilmTestSuite(count: Int): List<DbFavouriteFilm> {
        return mutableListOf<DbFavouriteFilm>()
            .apply {
                for (id in 0L until count) {
                    add(createDbFavouriteFilm(id))
                }
            }
            .sortedByDescending { it.releaseDate }
    }

    private fun createDbFavouriteFilm(id: Long): DbFavouriteFilm {
        return DbFavouriteFilm(
            id = id,
            posterPath = if (id % 2 == 0L) "posterPath" else null,
            title = "Film $id",
            overview = "overview $id",
            releaseDate = id * 1_000
        )
    }

    private fun List<DbFilm>.toFilms(favourites: List<DbFavouriteFilm>): List<Film> {
        return map { film ->
            with(film) {
                val isFavourite = favourites.find { it.id == id } != null
                Film(id, posterPath, title, overview, releaseDate, isFavourite)
            }
        }
    }

    private fun List<DbFavouriteFilm>.toFilms(): List<Film> {
        return map { film ->
            with(film) {
                Film(id, posterPath, title, overview, releaseDate, true)
            }
        }
    }
}