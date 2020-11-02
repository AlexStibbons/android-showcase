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
): QueryUseCase<TvListDomain, Unit>() {

    override suspend fun run(params: Unit?): Response<Failure, TvListDomain> {
        if (!networkHandler.isConnected) return Response.failure(Failure.NetworkConnection)

        val response = tvRepo.getPopular(1)

        return response.mapper { it.toTvListDomain() }
    }

}

