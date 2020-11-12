package com.alexstibbons.showcase.movieApi

import com.alexstibbons.showcase.BuildConfig
import com.alexstibbons.showcase.SearchTermsRepo
import com.alexstibbons.showcase.exhaustive
import com.alexstibbons.showcase.movieApi.model.FilmDetailsEntity
import com.alexstibbons.showcase.movieApi.model.FilmListResponse
import com.alexstibbons.showcase.network.NetworkResponse
import com.alexstibbons.showcase.network.NetworkResponse.Companion.parseResponse
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response

internal class MovieRepositoryImpl(
    private val movieApi: MovieApi
) : MovieRepository {

    private val apiKey = BuildConfig.MOVIE_DB_KEY

    override suspend fun getMovie(id: Int): Response<Failure, FilmDetailsEntity> {

        val networkResponse = try {
            movieApi
                .getMovie(id = id, apiKey = apiKey)
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

    override suspend fun getFilms(page: Int, searchTerms: SearchTermsRepo?): Response<Failure, FilmListResponse> {

        val networkResponse = if (searchTerms != null) {
            fetchSearch(page, searchTerms)
        } else {
            try {
                movieApi
                    .getPopularMovies(page = page, apiKey = apiKey)
                    .parseResponse()
            } catch (e: Exception) {
                return Response.failure(Failure.ServerError)
            }
        }

        return when (networkResponse) {
            is NetworkResponse.SuccessResponse -> Response.success(networkResponse.value)
            is NetworkResponse.EmptyBodySuccess -> Response.failure(MediaFailure.NoSuchMedia)
            is NetworkResponse.ErrorResponse -> Response.failure(Failure.ServerError)
        }.exhaustive
    }

    private suspend fun fetchSearch(page: Int, searchTerms: SearchTermsRepo): NetworkResponse<FilmListResponse> {
        return if (!searchTerms.title.isNullOrBlank()) {
            fetchByTitle(page, searchTerms)
        } else {
            try {
                movieApi
                    .getPopularMovies(page = page, apiKey = apiKey)
                    .parseResponse()
            } catch (e: Exception) {
                return NetworkResponse.ErrorResponse("Error", 500)
            }
        }

    }

    private suspend fun fetchByTitle(page: Int, searchTerms: SearchTermsRepo): NetworkResponse<FilmListResponse> {
        val genreIds: List<Int> = searchTerms.genreList.map { it.id }

        val networkResponse = try {
            movieApi
                .searchByTitle(page = page, apiKey = apiKey, query = searchTerms.title)
                .parseResponse()
        } catch (e: Exception) {
            return  NetworkResponse.ErrorResponse("Error", 500)
        }

        return when (networkResponse) {
            is NetworkResponse.SuccessResponse -> networkResponse.toFilteredResults(genreIds)
            is NetworkResponse.EmptyBodySuccess -> networkResponse
            is NetworkResponse.ErrorResponse -> networkResponse
        }.exhaustive
    }

    private fun NetworkResponse.SuccessResponse<FilmListResponse>.toFilteredResults(genreIds: List<Int>): NetworkResponse.SuccessResponse<FilmListResponse> = NetworkResponse.SuccessResponse<FilmListResponse>(
        value = FilmListResponse(
            value.page,
            value.total_results,
            value.total_pages,
            if (genreIds.isNotEmpty()) value.results.filter { item -> item.genre_ids.any { it in genreIds } } else value.results
        ),
        responseCode = 200)
}