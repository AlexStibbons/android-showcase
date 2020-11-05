package com.alexstibbons.showcase.details.domain

import com.alexstibbons.showcase.BASE_YOUTUBE_VIDEO_URL
import com.alexstibbons.showcase.movieApi.model.FilmDetailsEntity
import com.alexstibbons.showcase.movieApi.model.FilmListItemEntity
import com.alexstibbons.showcase.tvApi.model.TvShowListItem

private const val IMDB_BASE = "https://www.imdb.com/title/"

internal sealed class MediaDetailsModel(
    open val title: String,
    open val tagline: String,
    open val overview: String,
    open val imageUrl: String,
    open val imdbUrl: String?,
    open val trailer: Trailer?
) {
    data class FilmDetails(
        override val title: String,
        override val tagline: String,
        override val overview: String,
        override val imageUrl: String,
        override val imdbUrl: String?,
        override val trailer: Trailer?
    ) : MediaDetailsModel(title, tagline, overview, imageUrl, imdbUrl, trailer)

    data class TvDetails(
        override val title: String,
        override val overview: String,
        override val imageUrl: String
    ) : MediaDetailsModel(title, "", overview, imageUrl, null, null)
}


internal fun FilmDetailsEntity.toFilmDetails() = MediaDetailsModel.FilmDetails(
    title,
    tagline,
    overview,
    poster_path ?: "",
    IMDB_BASE+imdb_id,
    if (this.videos.results[0] != null) {
        Trailer(videos.results[0].name, videos.results[0].youtubeLink())
    } else null
)

internal fun TvShowListItem.toTvDetails() = MediaDetailsModel.TvDetails(
    name,
    overview,
    poster_path ?: ""
)

internal data class Trailer(
    val title: String,
    val youtubeLink: String
)