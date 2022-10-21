package com.doskoch.template.api.jikan.services.top

import androidx.annotation.IntRange
import com.doskoch.template.api.jikan.common.enum.RemoteAnimeFilter
import com.doskoch.template.api.jikan.common.enum.RemoteAnimeType
import com.doskoch.template.api.jikan.interceptors.ApiVersion
import com.doskoch.template.api.jikan.services.top.responses.GetTopAnimeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TopService {

    @ApiVersion(4)
    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("type") type: RemoteAnimeType,
        @Query("filter") filter: RemoteAnimeFilter,
        @IntRange(from = 1)
        @Query("page") page: Int,
        @IntRange(from = 0)
        @Query("limit") limit: Int
    ): GetTopAnimeResponse
}