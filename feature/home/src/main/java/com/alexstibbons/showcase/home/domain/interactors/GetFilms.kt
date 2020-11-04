package com.alexstibbons.showcase.home.domain.interactors

import com.alexstibbons.showcase.home.domain.MovieListDomain
import com.alexstibbons.showcase.home.domain.toMovieListdomain
import com.alexstibbons.showcase.interactors.QueryUseCase
import com.alexstibbons.showcase.movieApi.MovieRepository
import com.alexstibbons.showcase.network.NetworkHandler
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response
import com.alexstibbons.showcase.responses.mapper

internal class GetFilms(
    private val networkHandler: NetworkHandler,
    private val filmRepository: MovieRepository
) : QueryUseCase<MovieListDomain, Int>() {

    override suspend fun run(params: Int?): Response<Failure, MovieListDomain> {
        require( params != null) { "Page number cannot be null" }

        if (!networkHandler.isConnected) return Response.failure(Failure.NetworkConnection)

        val response = filmRepository.getFilms(params)

        return response.mapper { it.toMovieListdomain() }
    }

}