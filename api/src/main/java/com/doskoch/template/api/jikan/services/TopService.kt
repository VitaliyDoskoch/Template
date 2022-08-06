package com.doskoch.template.api.jikan.services

import androidx.annotation.IntRange
import com.doskoch.template.api.jikan.common.enum.AnimeFilter
import com.doskoch.template.api.jikan.common.enum.AnimeType
import com.doskoch.template.api.jikan.interceptors.ApiVersion
import com.doskoch.template.api.jikan.services.responses.GetTopAnimeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TopService {

    @ApiVersion(4)
    @GET("top/anime")
    fun getTopAnime(
        @Query("type") type: AnimeType,
        @Query("filter") filter: AnimeFilter,
        @IntRange(from = 1)
        @Query("page") page: Int,
        @IntRange(from = 0)
        @Query("limit") limit: Int
    ): GetTopAnimeResponse
}