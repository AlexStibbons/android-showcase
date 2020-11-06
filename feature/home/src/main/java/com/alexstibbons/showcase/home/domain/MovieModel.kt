package com.alexstibbons.showcase.home.domain

import com.alexstibbons.showcase.*
import com.alexstibbons.showcase.movieApi.model.FilmListItemEntity
import com.alexstibbons.showcase.movieApi.model.FilmListResponse

internal data class MovieListDomain(
    override val page: Int,
    override val data: List<MovieDomain>
): MediaList(page, data)

internal data class MovieDomain(
    override val id: Int,
    override val title: String,
    override val promo: String,
    override val imageUrl: String,
    override val genreList: List<Genre>
): MediaModel(id, title, promo, imageUrl, genreList, MediaType.FILM) {

    override fun toString(): String = "$title"
}

internal fun FilmListResponse.toMovieListDomain() = MovieListDomain(
    page,
    results.mapToListOf { it.toMovieDomain() }
)

internal fun FilmListItemEntity.toMovieDomain() = MovieDomain(
    id,
    title,
    overview,
    poster_path ?: "",
    genreList = this.toGenresEnum()
)
