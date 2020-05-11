package com.doskoch.movies.features.films_all.repository.api

import android.annotation.SuppressLint
import com.doskoch.apis.the_movie_db.services.discover.responses.GetMoviesResponse
import com.doskoch.movies.core.components.exceptions.ExpectedException.ErrorCode.FAILED_TO_LOAD_DATA
import com.doskoch.movies.core.functions.toDetermined
import com.doskoch.movies.features.films.functions.convertDate
import com.extensions.retrofit.components.INVALID_RESPONSE
import com.extensions.retrofit.components.exceptions.NetworkException
import io.reactivex.Single
import retrofit2.Response

class AllFilmsApiRepository(private val module: AllFilmsApiRepositoryModule) {

    fun load(pageKey: Int): Single<GetMoviesResponse> {
        val fromReleaseDate = module.fromReleaseDate()
        val toReleaseDate = module.toReleaseDate()

        return module.discoverServiceConnector.call {
            getMovies(pageKey, fromReleaseDate, toReleaseDate)
        }
            .flatMap { response ->
                validate(fromReleaseDate, toReleaseDate, response)
            }
            .onErrorResumeNext { Single.error(it.toDetermined(FAILED_TO_LOAD_DATA)) }
    }

    private fun validate(fromReleaseDate: String,
                         toReleaseDate: String,
                         response: Response<GetMoviesResponse>): Single<GetMoviesResponse> {
        val invalidItems =
            collectInvalidItems(fromReleaseDate, toReleaseDate, response.body()!!.results)

        return if (invalidItems.isEmpty()) {
            Single.just(response.body()!!)
        } else {
            Single.error(
                NetworkException(
                    response.raw().request.url.toString(),
                    NetworkException.Type.SERVER,
                    INVALID_RESPONSE,
                    invalidReleaseDateDescription(fromReleaseDate, toReleaseDate, invalidItems)
                )
            )
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun collectInvalidItems(fromReleaseDate: String,
                                    toReleaseDate: String,
                                    items: List<GetMoviesResponse.Result>): List<GetMoviesResponse.Result> {
        val from = convertDate(fromReleaseDate)
        val to = convertDate(toReleaseDate)

        return items.filter { convertDate(it.releaseDate) !in from..to }
    }

    private fun invalidReleaseDateDescription(fromReleaseDate: String,
                                              toReleaseDate: String,
                                              invalidItems: List<GetMoviesResponse.Result>): String {
        return "%s (from %s to %s):%s"
            .format(
                "${GetMoviesResponse::class.java.simpleName} contains items with release date " +
                    "that is not in the requested period",
                fromReleaseDate,
                toReleaseDate,
                invalidItems.joinToString(separator = ";\n", prefix = "\n")
            )
    }
}