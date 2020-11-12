package com.alexstibbons.showcase.tvApi

import com.alexstibbons.showcase.BuildConfig
import com.alexstibbons.showcase.SearchTermsRepo
import com.alexstibbons.showcase.exhaustive
import com.alexstibbons.showcase.movieApi.MediaFailure
import com.alexstibbons.showcase.network.NetworkResponse
import com.alexstibbons.showcase.network.NetworkResponse.Companion.parseResponse
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response
import com.alexstibbons.showcase.tvApi.model.TvListResponse
import com.alexstibbons.showcase.tvApi.model.TvShowDetailsEntity
import com.alexstibbons.showcase.tvApi.model.TvShowListItemEntity
import java.lang.Exception

internal class TvRepositoryImpl(
    private val api: TvApi
) : TvRepository{

    private val apiKey = BuildConfig.MOVIE_DB_KEY

    override suspend fun getPopular(page: Int, searchTerms: SearchTermsRepo?): Response<Failure, TvListResponse> {

        if (searchTerms != null) fetchSearch(page, searchTerms)

        val networkResponse: NetworkResponse<TvListResponse> = try {
            api
                .getPopularTvShows(apiKey = apiKey, page = page)
                .parseResponse()
        } catch (e: Exception) {
            return Response.failure(Failure.ServerError)
        }

        return when (networkResponse) {
            is NetworkResponse.SuccessResponse -> Response.success(networkResponse.value)
            is NetworkResponse.EmptyBodySuccess -> Response.failure(MediaFailure.EmptyMediaList)
            is NetworkResponse.ErrorResponse -> Response.failure(Failure.ServerError)
        }.exhaustive
    }

    private suspend fun fetchSearch(page: Int, searchTerms: SearchTermsRepo): Response<Failure, TvListResponse> {
        val networkResponse: NetworkResponse<TvListResponse> = try {
            api
                .getPopularTvShows(apiKey = apiKey, page = page)
                .parseResponse()
        } catch (e: Exception) {
            return Response.failure(Failure.ServerError)
        }

        return when (networkResponse) {
            is NetworkResponse.SuccessResponse -> Response.success(networkResponse.value)
            is NetworkResponse.EmptyBodySuccess -> Response.failure(MediaFailure.EmptyMediaList)
            is NetworkResponse.ErrorResponse -> Response.failure(Failure.ServerError)
        }.exhaustive
    }

    override suspend fun getTvShowDetails(id: Int): Response<Failure, TvShowDetailsEntity> {

        val networkResponse: NetworkResponse<TvShowDetailsEntity> = try {
            api
                .getTvShow(id = id, apiKey = apiKey)
                .parseResponse()
        } catch (e: Exception) {
            return Response.failure(Failure.ServerError)
        }

        return when (networkResponse) {
            is NetworkResponse.SuccessResponse -> Response.success(networkResponse.value)
            is NetworkResponse.EmptyBodySuccess -> Response.failure(MediaFailure.NoSuchMedia)
            is NetworkResponse.ErrorResponse -> Response.failure(Failure.ServerError)
        }.exhaustive
    }
}