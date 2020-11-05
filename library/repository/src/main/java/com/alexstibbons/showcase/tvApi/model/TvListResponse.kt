package com.alexstibbons.showcase.tvApi.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexstibbons.showcase.mapToListOf
import com.alexstibbons.showcase.movieApi.model.Genre
import com.alexstibbons.showcase.movieApi.model.toTvGenreEnum


data class TvListResponse(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<TvShow>)

@Entity
data class TvShow(

    @PrimaryKey
    val id: Int,

    val name: String,

    val overview: String,

    val poster_path: String?,

    val genres: List<Genre>

){
    override fun toString(): String = "$name"
    fun toGenreEnum() = genres.mapToListOf { it.toTvGenreEnum() }
}