package com.alexstibbons.showcase.tvApi

import com.alexstibbons.showcase.tvApi.model.TvListResponse
import com.alexstibbons.showcase.tvApi.model.TvShowDetailsEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvApi {

    @GET("tv/popular")
    suspend fun getPopularTvShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<TvListResponse>

    @GET("tv/{tv_id}")
    suspend fun getTvShow(
        @Path("tv_id") id: Int,
        @Query("append_to_response") videos: String = "videos",
        @Query("api_key") apiKey: String
    ): Response<TvShowDetailsEntity>

    @GET("search/tv")
    suspend fun searchByTitle(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("query") query: String
    ): Response<TvListResponse>

    @GET("discover/tv")
    suspend fun searchByGenre(
        @Query("sort_by") sort: String = "popularity.desc",
        @Query("include_adult") isAdult: Boolean = false,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String,
        @Query("with_genres") genres: String
    ): Response<TvListResponse>
}