package com.alexstibbons.showcase.home.domain.interactors

import com.alexstibbons.showcase.home.domain.MovieListDomain
import com.alexstibbons.showcase.home.domain.toMovieListDomain
import com.alexstibbons.showcase.interactors.QueryUseCase
import com.alexstibbons.showcase.movieApi.MovieRepository
import com.alexstibbons.showcase.network.NetworkHandler
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response
import com.alexstibbons.showcase.responses.mapSuccessTo
import com.alexstibbons.showcase.search.SearchTerms

internal class GetFilms(
    private val networkHandler: NetworkHandler,
    private val filmRepository: MovieRepository
) : QueryUseCase<MovieListDomain, GetFilms.Params>() {

    override suspend fun run(params: Params?): Response<Failure, MovieListDomain> {
        require( params != null) { "Page number cannot be null" }

        if (!networkHandler.isConnected) return Response.failure(Failure.NetworkConnection)

        val response = filmRepository.getFilms(params.currentPage, params.searchTerms?.toSearchTermsRepo())

        return response.mapSuccessTo { it.toMovieListDomain() }
    }

    data class Params(
        val currentPage: Int,
        val searchTerms: SearchTerms?
    )
}