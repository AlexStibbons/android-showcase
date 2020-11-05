package com.alexstibbons.showcase.tvApi

import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response
import com.alexstibbons.showcase.tvApi.model.TvListResponse
import com.alexstibbons.showcase.tvApi.model.TvShowDetailsEntity
import com.alexstibbons.showcase.tvApi.model.TvShowListItemEntity

interface TvRepository {

    suspend fun getPopular(page: Int): Response<Failure, TvListResponse>

    suspend fun getTvShowDetails(id: Int): Response<Failure, TvShowDetailsEntity>
}