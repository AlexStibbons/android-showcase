package com.alexstibbons.showcase.home.domain.interactors

import com.alexstibbons.showcase.exhaustive
import com.alexstibbons.showcase.interactors.QueryUseCase
import com.alexstibbons.showcase.movieApi.MovieRepository
import com.alexstibbons.showcase.movieApi.model.Movie
import com.alexstibbons.showcase.network.NetworkHandler
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response
import com.alexstibbons.showcase.responses.mapper

internal class GetFilms(
    private val networkHandler: NetworkHandler,
    private val filmRepository: MovieRepository
) : QueryUseCase<List<Movie>, Unit>() {

    override suspend fun run(params: Unit?): Response<Failure, List<Movie>> {
        if (!networkHandler.isConnected) return Response.failure(Failure.NetworkConnection)

        val response = filmRepository.getFilms()

        return response.mapper { it }
    }

}