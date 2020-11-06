package com.alexstibbons.showcase.details.domain

import com.alexstibbons.showcase.BASE_IMG_URL
import com.alexstibbons.showcase.Genre
import com.alexstibbons.showcase.movieApi.model.FilmDetailsEntity
import com.alexstibbons.showcase.tvApi.model.TvShowDetailsEntity
import com.alexstibbons.showcase.tvApi.model.TvShowListItemEntity

private const val IMDB_BASE = "https://www.imdb.com/title/"

internal sealed class MediaDetailsModel(
    open val title: String,
    open val tagline: String?,
    open val overview: String,
    open val imageUrl: String,
    open val imdbUrl: String?,
    open val trailer: Trailer?,
    open val genres: List<Genre>?
) {
    data class FilmDetails(
        override val title: String,
        override val tagline: String?,
        override val overview: String,
        override val imageUrl: String,
        override val imdbUrl: String?,
        override val trailer: Trailer?,
        override val genres: List<Genre>
    ) : MediaDetailsModel(title, tagline, overview, imageUrl, imdbUrl, trailer, genres)

    data class TvDetails(
        override val title: String,
        override val overview: String,
        override val imageUrl: String,
        override val trailer: Trailer?,
        override val genres: List<Genre>?
    ) : MediaDetailsModel(title, null, overview, imageUrl, null, trailer, genres)
}


internal fun FilmDetailsEntity.toFilmDetails() = MediaDetailsModel.FilmDetails(
    title,
    tagline,
    overview,
    BASE_IMG_URL+poster_path ?: "",
    IMDB_BASE+imdb_id,
    if (this.videos.results.isNotEmpty()) {
        Trailer(videos.results[0].name, videos.results[0].youtubeLink())
    } else null,
    this.toGenresEnum()
)

internal fun TvShowDetailsEntity.toTvDetails() = MediaDetailsModel.TvDetails(
    name,
    overview,
    BASE_IMG_URL+poster_path ?: "",
    if (this.videos.results[0] != null) {
        Trailer(videos.results[0].name, videos.results[0].youtubeLink())
    } else null,
    this.toGenresEnum()
)

internal data class Trailer(
    val title: String,
    val youtubeLink: String
)