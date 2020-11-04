package com.alexstibbons.showcase.details.domain.interactor

import com.alexstibbons.showcase.details.domain.MediaModel
import com.alexstibbons.showcase.details.domain.toTvDetails
import com.alexstibbons.showcase.interactors.QueryUseCase
import com.alexstibbons.showcase.network.NetworkHandler
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response
import com.alexstibbons.showcase.responses.mapSuccessTo
import com.alexstibbons.showcase.tvApi.TvRepository

internal data class GetTvDetails(
    private val networkHandler: NetworkHandler,
    private val tvRepository: TvRepository
) : QueryUseCase<MediaModel.TvDetails, Int>() {
    override suspend fun run(params: Int?): Response<Failure, MediaModel.TvDetails> {
        require(params != null) { "Media id cannot be null" }
        if (!networkHandler.isConnected) return Response.failure(Failure.NetworkConnection)

        val response = tvRepository.getTvShowDetails(params)

        return response.mapSuccessTo { it.toTvDetails() }
    }

}