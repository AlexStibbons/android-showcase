package com.alexstibbons.showcase.tvApi

import com.alexstibbons.showcase.tvApi.model.TvListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TvApi {

    @GET("tv/popular")
    suspend fun getPopularTvShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<TvListResponse>
}