package com.alexstibbons.showcase.tvApi

import com.alexstibbons.showcase.tvApi.model.TvListResponse
import com.alexstibbons.showcase.tvApi.model.TvShowListItem
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
        @Query("api_key") apiKey: String
    ): Response<TvShowListItem>
}