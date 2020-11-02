package com.alexstibbons.showcase.tvApi

import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response
import com.alexstibbons.showcase.tvApi.model.TvListResponse

interface TvRepository {

    suspend fun getPopular(page: Int): Response<Failure, TvListResponse>
}