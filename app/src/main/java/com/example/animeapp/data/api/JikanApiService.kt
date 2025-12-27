package com.example.animeapp.data.api

import com.example.animeapp.data.model.AnimeCharactersResponse
import com.example.animeapp.data.model.AnimeDetailResponse
import com.example.animeapp.data.model.TopAnimeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JikanApiService {
    @GET("top/anime")
    suspend fun getTopAnime(@Query("page") page: Int): Response<TopAnimeResponse>

    @GET("anime/{id}")
    suspend fun getAnimeDetails(@Path("id") id: Int): Response<AnimeDetailResponse>

    @GET("anime")
    suspend fun searchAnime(@Query("q") query: String): Response<TopAnimeResponse>

    @GET("anime/{id}/characters")
    suspend fun getAnimeCharacters(@Path("id") id: Int): Response<AnimeCharactersResponse>
}
