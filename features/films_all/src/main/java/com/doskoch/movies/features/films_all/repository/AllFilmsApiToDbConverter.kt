package com.doskoch.movies.features.films_all.repository

import com.doskoch.apis.the_movie_db.services.discover.responses.GetMoviesResponse
import com.doskoch.movies.database.modules.films.entity.DbFilm
import com.doskoch.movies.features.films.functions.convertDate

class AllFilmsApiToDbConverter {

    fun toDbFilm(item: GetMoviesResponse.Result): DbFilm {
        return with(item) {
            DbFilm(
                id = id,
                posterPath = posterPath,
                title = title,
                overview = overview,
                releaseDate = convertDate(releaseDate)
            )
        }
    }

    fun toDbFilms(items: List<GetMoviesResponse.Result>): List<DbFilm> {
        return items.map(this::toDbFilm)
    }
}