package com.alexstibbons.showcase.home.domain.interactors

import com.alexstibbons.showcase.home.domain.TvListDomain
import com.alexstibbons.showcase.home.domain.toTvListDomain
import com.alexstibbons.showcase.interactors.QueryUseCase
import com.alexstibbons.showcase.network.NetworkHandler
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response
import com.alexstibbons.showcase.responses.mapper
import com.alexstibbons.showcase.tvApi.TvRepository

internal data class GetTv(
    private val networkHandler: NetworkHandler,
    private val tvRepo: TvRepository
): QueryUseCase<TvListDomain, Int>() {

    override suspend fun run(params: Int?): Response<Failure, TvListDomain> {
        require(params != null) { "Page number cannot be null" }
        if (!networkHandler.isConnected) return Response.failure(Failure.NetworkConnection)

        val response = tvRepo.getPopular(params)

        return response.mapper { it.toTvListDomain() }
    }

}

