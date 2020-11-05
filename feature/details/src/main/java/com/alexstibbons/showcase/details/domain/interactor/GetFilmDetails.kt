package com.alexstibbons.showcase.details.domain.interactor

import com.alexstibbons.showcase.details.domain.MediaDetailsModel
import com.alexstibbons.showcase.details.domain.toFilmDetails
import com.alexstibbons.showcase.interactors.QueryUseCase
import com.alexstibbons.showcase.movieApi.MovieRepository
import com.alexstibbons.showcase.network.NetworkHandler
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response
import com.alexstibbons.showcase.responses.mapSuccessTo

internal data class GetFilmDetails(
    private val networkHandler: NetworkHandler,
    private val filmRepo: MovieRepository
): QueryUseCase<MediaDetailsModel.FilmDetails, Int>() {

    override suspend fun run(params: Int?): Response<Failure, MediaDetailsModel.FilmDetails> {
        require(params != null) { "Media id cannot be null" }
        if (!networkHandler.isConnected) return Response.failure(Failure.NetworkConnection)

        val response = filmRepo.getMovie(params)

        return response.mapSuccessTo { it.toFilmDetails() }
    }
}