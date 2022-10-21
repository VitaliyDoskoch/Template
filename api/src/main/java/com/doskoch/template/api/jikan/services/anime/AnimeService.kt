package com.doskoch.template.api.jikan.services.anime

import com.doskoch.template.api.jikan.interceptors.ApiVersion
import com.doskoch.template.api.jikan.services.anime.responses.GetAnimeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeService {

    @ApiVersion(4)
    @GET("anime/{id}")
    suspend fun getAnime(@Path("id") id: Int): GetAnimeResponse
}