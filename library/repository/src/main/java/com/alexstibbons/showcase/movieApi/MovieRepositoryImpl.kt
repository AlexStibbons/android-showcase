package com.alexstibbons.showcase.movieApi

import com.alexstibbons.showcase.network.NetworkResponse
import com.alexstibbons.showcase.network.NetworkResponse.Companion.parseResponse
import com.alexstibbons.showcase.exhaustive
import com.alexstibbons.showcase.movieApi.model.Movie
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response
import java.lang.Exception

internal class MovieRepositoryImpl(
    private val movieApi: MovieApi,
    private val apiKey: String
) : MovieRepository {

    override suspend fun getMovie(id: Int): Response<Failure, Movie> {

        val networkResponse = try {
            movieApi
                .getMovie(id, apiKey)
                .parseResponse()
        } catch (e: Exception) {
            return Response.failure(Failure.ServerError)
        }

        return when (networkResponse) {
            is NetworkResponse.SuccessResponse -> Response.success(networkResponse.value)
            is NetworkResponse.EmptyBodySuccess -> Response.failure(MovieFailure.NoSuchMovie)
            is NetworkResponse.ErrorResponse -> Response.failure(Failure.ServerError)
        }.exhaustive
    }

}