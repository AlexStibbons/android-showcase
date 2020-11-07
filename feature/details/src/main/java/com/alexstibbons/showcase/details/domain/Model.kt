package com.alexstibbons.showcase.details.domain

import com.alexstibbons.showcase.BASE_IMG_URL
import com.alexstibbons.showcase.Genre
import com.alexstibbons.showcase.MediaType
import com.alexstibbons.showcase.database.FaveEntity
import com.alexstibbons.showcase.movieApi.model.FilmDetailsEntity
import com.alexstibbons.showcase.tvApi.model.TvShowDetailsEntity
import com.alexstibbons.showcase.tvApi.model.TvShowListItemEntity

private const val IMDB_BASE = "https://www.imdb.com/title/"

internal sealed class MediaDetailsModel(
    open val id: Int,
    open val title: String,
    open val tagline: String?,
    open val overview: String,
    open val imageUrl: String,
    open val imdbUrl: String?,
    open val trailer: Trailer?,
    open val genres: List<Genre>?,
    open val type: MediaType
) {
    data class FilmDetails(
        override val id: Int,
        override val title: String,
        override val tagline: String?,
        override val overview: String,
        override val imageUrl: String,
        override val imdbUrl: String?,
        override val trailer: Trailer?,
        override val genres: List<Genre>
    ) : MediaDetailsModel(id, title, tagline, overview, imageUrl, imdbUrl, trailer, genres, MediaType.FILM)

    data class TvDetails(
        override val id: Int,
        override val title: String,
        override val overview: String,
        override val imageUrl: String,
        override val trailer: Trailer?,
        override val genres: List<Genre>?
    ) : MediaDetailsModel(id, title, null, overview, imageUrl, null, trailer, genres, MediaType.TV)
}


internal fun FilmDetailsEntity.toFilmDetails() = MediaDetailsModel.FilmDetails(
    id,
    title,
    tagline,
    overview,
    poster_path ?: "",
    IMDB_BASE+imdb_id,
    if (this.videos.results.isNotEmpty()) {
        Trailer(videos.results[0].name, videos.results[0].youtubeLink())
    } else null,
    this.toGenresEnum()
)

internal fun TvShowDetailsEntity.toTvDetails() = MediaDetailsModel.TvDetails(
    id,
    name,
    overview,
    poster_path ?: "",
    if (this.videos.results.isNotEmpty()) {
        Trailer(videos.results[0].name, videos.results[0].youtubeLink())
    } else null,
    this.toGenresEnum()
)

internal data class Trailer(
    val title: String,
    val youtubeLink: String
)

internal fun MediaDetailsModel.toFaveEntity() = FaveEntity(
    id,
    title,
    overview,
    genres?.joinToString { it.title } ?: "",
    imageUrl,
    if (this is MediaDetailsModel.FilmDetails) MediaType.FILM.id else MediaType.TV.id
)