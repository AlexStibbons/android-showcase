package com.alexstibbons.showcase.home.domain

import com.alexstibbons.showcase.home.MediaModel
import com.alexstibbons.showcase.movieApi.model.Movie
import com.alexstibbons.showcase.movieApi.model.MovieListResponse

internal data class MovieListDomain(
    val page: Int,
    val data: List<MovieDomain>
)

internal data class MovieDomain(
    override val id: Int,
    override val title: String,
    override val promo: String,
    override val imageUrl: String
): MediaModel(id, title, promo, imageUrl) {

    override fun toString(): String = "$title"
}

internal fun MovieListResponse.toMovieListdomain() = MovieListDomain(
    page,
    results.toMovieListDomain()
)

internal fun Movie.toMovieDomain() = MovieDomain(
    id,
    title,
    overview,
    poster_path
)

internal fun List<Movie>.toMovieListDomain(): List<MovieDomain> {
    val list = mutableListOf<MovieDomain>()
    this.forEach {
        list.add(it.toMovieDomain())
    }
    return list.toList()
}

