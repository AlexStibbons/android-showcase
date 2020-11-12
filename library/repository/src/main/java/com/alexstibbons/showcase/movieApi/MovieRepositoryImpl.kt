package com.alexstibbons.showcase.movieApi

import android.util.Log
import com.alexstibbons.showcase.BuildConfig
import com.alexstibbons.showcase.SearchTermsRepo
import com.alexstibbons.showcase.network.NetworkResponse
import com.alexstibbons.showcase.network.NetworkResponse.Companion.parseResponse
import com.alexstibbons.showcase.exhaustive
import com.alexstibbons.showcase.movieApi.model.FilmDetailsEntity
import com.alexstibbons.showcase.movieApi.model.FilmListResponse
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response
import java.lang.Exception

internal class MovieRepositoryImpl(
    private val movieApi: MovieApi
) : MovieRepository {

    private val apiKey = BuildConfig.MOVIE_DB_KEY

    override suspend fun getMovie(id: Int): Response<Failure, FilmDetailsEntity> {

        Log.e("in repo", "id: $id")
        val networkResponse = try {
            Log.e("in try", "id: $id")
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

        if (searchTerms != null) fetchSearch(page, searchTerms)

        val networkResponse = try {
            movieApi
                .getPopularMovies(page = page, apiKey = apiKey)
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

    private suspend fun fetchSearch(page: Int, searchTerms: SearchTermsRepo): Response<Failure, FilmListResponse> {

        if (!searchTerms.title.isNullOrBlank()) fetchByTitle(page, searchTerms)

        val networkResponse = try {
            movieApi
                .getPopularMovies(page = page, apiKey = apiKey)
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

    private suspend fun fetchByTitle(page: Int, searchTerms: SearchTermsRepo): Response<Failure, FilmListResponse> {
        val networkResponse = try {
            movieApi
                .getPopularMovies(page = page, apiKey = apiKey)
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