package com.alexstibbons.showcase.movieApi.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexstibbons.showcase.FilmGenre
import com.alexstibbons.showcase.TvGenre
import com.alexstibbons.showcase.mapToListOf

data class MovieListResponse(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<Movie>)

@Entity
data class Movie(

    @PrimaryKey
    val id: Int,

    val title: String,

    val overview: String,

    val poster_path: String?,

    val imdb_id: String?,

    val genres: List<Genre>

){
    override fun toString(): String = "$title"
    fun toGenresEnum() = genres.mapToListOf { it.toFilmGenreEnum() }
}

data class Genre(
    val id: Int,
    val name: String
)

internal fun Genre.toFilmGenreEnum() = FilmGenre.from(this.id)
internal fun Genre.toTvGenreEnum() = TvGenre.from(this.id)