package com.alexstibbons.showcase.movieApi

import com.alexstibbons.showcase.movieApi.model.FilmDetailsEntity
import com.alexstibbons.showcase.movieApi.model.FilmListResponse
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response

interface MovieRepository {
    suspend fun getMovie(id: Int): Response<Failure, FilmDetailsEntity>

    suspend fun getFilms(page: Int): Response<Failure, FilmListResponse>
}