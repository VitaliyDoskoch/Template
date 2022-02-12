package com.doskoch.api.the_movie_db.services.discover

import androidx.annotation.IntRange
import com.doskoch.api.the_movie_db.services.discover.responses.GetMoviesResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverService {

    @GET("discover/movie")
    fun getMovies(
        @Query("page")
        @IntRange(from = 1)
        pageKey: Int,
        @Query("primary_release_date.gte")
        fromReleaseDate: String,
        @Query("primary_release_date.lte")
        toReleaseDate: String,
        @Query("sort_by")
        sortBy: String = "primary_release_date.desc"
    ): Single<Response<GetMoviesResponse>>

}