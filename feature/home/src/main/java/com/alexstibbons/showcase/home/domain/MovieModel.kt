package com.alexstibbons.showcase.home.domain

import com.alexstibbons.showcase.MediaList
import com.alexstibbons.showcase.MediaModel
import com.alexstibbons.showcase.mapToListOf
import com.alexstibbons.showcase.movieApi.model.Movie
import com.alexstibbons.showcase.movieApi.model.MovieListResponse

internal data class MovieListDomain(
    override val page: Int,
    override val data: List<MovieDomain>
): MediaList(page, data)

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
    results.mapToListOf { it.toMovieDomain() }
)

internal fun Movie.toMovieDomain() = MovieDomain(
    id,
    title,
    overview,
    poster_path ?: ""
)
