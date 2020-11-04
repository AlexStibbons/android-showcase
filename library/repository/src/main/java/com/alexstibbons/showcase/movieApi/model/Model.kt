package com.alexstibbons.showcase.movieApi.model

import androidx.room.Entity
import androidx.room.PrimaryKey

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

    val imdb_id: String?

){
    override fun toString(): String = "$title"
}