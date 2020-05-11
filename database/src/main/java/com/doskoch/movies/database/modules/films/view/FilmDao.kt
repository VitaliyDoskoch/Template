package com.doskoch.movies.database.modules.films.view

import androidx.room.Dao
import androidx.room.Query

@Dao
abstract class FilmDao {

    @Query(
        """
            select 
            *, 
            case when exists(select rowId from DbFavouriteFilm where DbFilm.id = DbFavouriteFilm.id) 
            then 1 else 0 end as isFavourite 
            from DbFilm
            order by releaseDate desc
            limit :limit offset :offset
        """
    )
    abstract fun selectAll(limit: Int, offset: Int): List<Film>

    @Query(
        """
            select *, 1 as isFavourite from DbFavouriteFilm
            order by releaseDate desc
            limit :limit offset :offset
        """
    )
    abstract fun selectFavourite(limit: Int, offset: Int): List<Film>
}