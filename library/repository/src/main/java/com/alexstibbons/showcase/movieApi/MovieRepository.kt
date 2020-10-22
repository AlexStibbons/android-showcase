package com.alexstibbons.showcase.movieApi

import com.alexstibbons.showcase.movieApi.model.Movie
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response


interface MovieRepository {
    suspend fun getMovie(id: Int): Response<Failure, Movie>
}