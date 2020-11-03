package com.alexstibbons.showcase.details.domain.interactor

import com.alexstibbons.showcase.details.domain.MediaModel
import com.alexstibbons.showcase.details.domain.toFilmDetails
import com.alexstibbons.showcase.interactors.QueryUseCase
import com.alexstibbons.showcase.movieApi.MovieRepository
import com.alexstibbons.showcase.network.NetworkHandler
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response
import com.alexstibbons.showcase.responses.mapper

internal data class GetFilmDetails(
    private val networkHandler: NetworkHandler,
    private val filmRepo: MovieRepository
): QueryUseCase<MediaModel.FilmDetails, Int>() {

    override suspend fun run(params: Int?): Response<Failure, MediaModel.FilmDetails> {
        require(params != null) { "Media id cannot be null" }
        if (!networkHandler.isConnected) return Response.failure(Failure.NetworkConnection)

        val response = filmRepo.getMovie(params)

        return response.mapper { it.toFilmDetails() }
    }
}