package com.alexstibbons.showcase.movieApi

import com.alexstibbons.showcase.movieApi.model.MovieListItem
import com.alexstibbons.showcase.movieApi.model.MovieListResponse
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response

interface MovieRepository {
    suspend fun getMovie(id: Int): Response<Failure, MovieListItem>

    suspend fun getFilms(page: Int): Response<Failure, MovieListResponse>
}