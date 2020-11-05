package com.alexstibbons.showcase.movieApi.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexstibbons.showcase.BASE_YOUTUBE_VIDEO_URL
import com.alexstibbons.showcase.FilmGenre
import com.alexstibbons.showcase.TvGenre
import com.alexstibbons.showcase.mapToListOf

data class FilmListResponse(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<FilmListItemEntity>)

@Entity
data class FilmListItemEntity(

    @PrimaryKey
    val id: Int,

    val title: String,

    val overview: String,

    val poster_path: String?,

    val imdb_id: String?,

    val genre_ids: List<Int>

){
    override fun toString(): String = "$title"
    fun toGenresEnum() = genre_ids.mapToListOf { FilmGenre.from(it) }
}

data class GenreEntity(
    val id: Int,
    val name: String
)

data class FilmDetailsEntity(
    val id: Int,
    val title: String,
    val tagline: String,
    val overview: String,
    val genres: List<GenreEntity>,
    val imdb_id: String?,
    val poster_path: String?,
    val videos: VideoWrapper
) {
    fun toGenresEnum() = genres.mapToListOf { FilmGenre.from(it.id) }
}

data class VideoWrapper(
    val results: List<VideoEntity>
)

data class VideoEntity(
    val id: String,
    val key: String,
    val site: String,
    val name: String
) {
    fun youtubeLink(): String = if (site.equals("YouTube", ignoreCase = true)) "$BASE_YOUTUBE_VIDEO_URL$key" else ""
}

internal fun GenreEntity.toFilmGenreEnum() = FilmGenre.from(this.id)
internal fun GenreEntity.toTvGenreEnum() = TvGenre.from(this.id)

