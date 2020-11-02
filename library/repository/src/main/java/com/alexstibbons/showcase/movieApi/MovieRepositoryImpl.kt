package com.alexstibbons.showcase.movieApi

import com.alexstibbons.showcase.BuildConfig
import com.alexstibbons.showcase.doWhenNull
import com.alexstibbons.showcase.network.NetworkResponse
import com.alexstibbons.showcase.network.NetworkResponse.Companion.parseResponse
import com.alexstibbons.showcase.exhaustive
import com.alexstibbons.showcase.movieApi.model.Movie
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response
import java.lang.Exception

internal class MovieRepositoryImpl(
    private val movieApi: MovieApi
) : MovieRepository {

    private val apiKey = BuildConfig.MOVIE_DB_KEY

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

    override suspend fun getFilms(): Response<Failure, List<Movie>> {

        val networkResponse = try {
            movieApi
                .getPopularMovies(page = 1, apiKey = apiKey)
                .parseResponse()
        } catch (e: Exception) {
            return Response.failure(Failure.ServerError)
        }

        return when (networkResponse) {
            is NetworkResponse.SuccessResponse -> Response.success(networkResponse.value.results)
            is NetworkResponse.EmptyBodySuccess -> Response.failure(MovieFailure.NoSuchMovie)
            is NetworkResponse.ErrorResponse -> Response.failure(Failure.ServerError)
        }.exhaustive
    }

}