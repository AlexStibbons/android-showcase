package com.alexstibbons.showcase.home.domain

import com.alexstibbons.showcase.*
import com.alexstibbons.showcase.database.FaveEntity
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
    override val genreList: List<Genre>,
    override val type: MediaType = MediaType.FILM
): MediaModel(id, title, promo, imageUrl, genreList, type) {

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

internal fun FaveEntity.toMediaModel(): MediaModel {
    val mediaType = MediaType.from(this.mediaType)
    return when (mediaType) {
        MediaType.FILM -> MovieDomain(id, title, overview, "", genreString.toGenreEnum())
        MediaType.TV -> TvShowDomain(id, title, overview, "", genreString.toGenreEnum())
        else -> error("Error when mapping fave to media model")
    }.exhaustive
}

internal fun String.toGenreEnum(): List<Genre> {
    val stringList: List<String> = this.split(", ")
    return stringList.mapToListOf { Genre.from(it) }
}