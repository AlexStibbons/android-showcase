package com.alexstibbons.showcase.movieApi

import com.alexstibbons.showcase.movieApi.model.FilmDetailsEntity
import com.alexstibbons.showcase.movieApi.model.FilmListResponse
import com.alexstibbons.showcase.tvApi.model.TvListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("discover/movie")
    suspend fun getPopularMovies(
        @Query("sort_by") sort: String = "popularity.desc",
        @Query("include_adult") isAdult: Boolean = false,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): Response<FilmListResponse>

    @GET("movie/{id}")
    suspend fun getMovie(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("append_to_response") videos: String = "videos"
    ): Response<FilmDetailsEntity>

    @GET("search/movie")
    suspend fun searchByTitle(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("query") query: String
    ): Response<FilmListResponse>
}