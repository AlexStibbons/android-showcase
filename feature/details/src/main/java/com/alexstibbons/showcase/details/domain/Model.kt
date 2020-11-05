package com.alexstibbons.showcase.details.domain

import com.alexstibbons.showcase.movieApi.model.MovieListItem
import com.alexstibbons.showcase.tvApi.model.TvShowListItem

private const val IMDB_BASE = "https://www.imdb.com/title/"

internal sealed class MediaModel(
    open val title: String,
    open val overview: String,
    open val imageUrl: String,
    open val imdbUrl: String?
) {
    data class FilmDetails(
        override val title: String,
        override val overview: String,
        override val imageUrl: String,
        override val imdbUrl: String?
    ) : MediaModel(title, overview, imageUrl, imdbUrl)

    data class TvDetails(
        override val title: String,
        override val overview: String,
        override val imageUrl: String
    ) : MediaModel(title, overview, imageUrl, null)
}


internal fun MovieListItem.toFilmDetails() = MediaModel.FilmDetails(
    title,
    overview,
    poster_path ?: "",
    IMDB_BASE+imdb_id
)

internal fun TvShowListItem.toTvDetails() = MediaModel.TvDetails(
    name,
    overview,
    poster_path ?: ""
)