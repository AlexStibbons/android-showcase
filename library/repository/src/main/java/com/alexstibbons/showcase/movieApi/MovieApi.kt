package com.alexstibbons.showcase.movieApi

import com.alexstibbons.showcase.movieApi.model.Movie
import com.alexstibbons.showcase.movieApi.model.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/top_rated")
    suspend fun getTopMovies(
        @Query("api_key") apiKey: String
    ): Response<MovieListResponse>

    @GET("discover/movie")
    suspend fun getPopularMovies(
        @Query("sort_by") sort: String = "popularity.desc",
        @Query("include_adult") isAdult: Boolean = false,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieListResponse>

    @GET("movie/{id}")
    suspend fun getMovie(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): Response<Movie>
}