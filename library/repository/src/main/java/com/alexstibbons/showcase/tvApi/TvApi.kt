package com.alexstibbons.showcase.tvApi

import com.alexstibbons.showcase.tvApi.model.TvListResponse
import com.alexstibbons.showcase.tvApi.model.TvShowDetailsEntity
import com.alexstibbons.showcase.tvApi.model.TvShowListItemEntity
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
}