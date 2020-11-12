package com.alexstibbons.showcase.home.domain.interactors

import com.alexstibbons.showcase.home.domain.TvListDomain
import com.alexstibbons.showcase.home.domain.toTvListDomain
import com.alexstibbons.showcase.interactors.QueryUseCase
import com.alexstibbons.showcase.network.NetworkHandler
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response
import com.alexstibbons.showcase.responses.mapSuccessTo
import com.alexstibbons.showcase.search.SearchTerms
import com.alexstibbons.showcase.tvApi.TvRepository

internal data class GetTv(
    private val networkHandler: NetworkHandler,
    private val tvRepo: TvRepository
): QueryUseCase<TvListDomain, GetTv.Params>() {

    override suspend fun run(params: Params?): Response<Failure, TvListDomain> {
        require(params != null) { "Page number cannot be null" }
        if (!networkHandler.isConnected) return Response.failure(Failure.NetworkConnection)

        val response = tvRepo.getPopular(params.currentPage, params.searchTerms?.toSearchTermsRepo())

        return response.mapSuccessTo { it.toTvListDomain() }
    }

    data class Params(
        val currentPage: Int,
        val searchTerms: SearchTerms?
    )

}

