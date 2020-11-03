package com.alexstibbons.showcase.details.domain

import com.alexstibbons.showcase.movieApi.model.Movie
import com.alexstibbons.showcase.tvApi.model.TvShow

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


internal fun Movie.toFilmDetails() = MediaModel.FilmDetails(
    title,
    overview,
    poster_path,
    imdb_id
)

internal fun TvShow.toTvDetails() = MediaModel.TvDetails(
    name,
    overview,
    poster_path ?: ""
)