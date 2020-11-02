package com.alexstibbons.showcase.tvApi

import com.alexstibbons.showcase.BuildConfig
import com.alexstibbons.showcase.exhaustive
import com.alexstibbons.showcase.movieApi.MovieFailure
import com.alexstibbons.showcase.network.NetworkResponse
import com.alexstibbons.showcase.network.NetworkResponse.Companion.parseResponse
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response
import com.alexstibbons.showcase.tvApi.model.TvListResponse
import java.lang.Exception

internal class TvRepositoryImpl(
    private val api: TvApi
) : TvRepository{

    private val apiKey = BuildConfig.MOVIE_DB_KEY

    override suspend fun getPopular(page: Int): Response<Failure, TvListResponse> {

        val networkResponse: NetworkResponse<TvListResponse> = try {
            api
                .getPopularTvShows(apiKey = apiKey, page = 1)
                .parseResponse()
        } catch (e: Exception) {
            return Response.failure(Failure.ServerError)
        }

        return when (networkResponse) {
            is NetworkResponse.SuccessResponse -> Response.success(networkResponse.value)
            is NetworkResponse.EmptyBodySuccess -> Response.failure(MovieFailure.EmptyMovieList)
            is NetworkResponse.ErrorResponse -> Response.failure(Failure.ServerError)
        }.exhaustive
    }
}