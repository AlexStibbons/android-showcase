package com.alexstibbons.showcase.tvApi.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexstibbons.showcase.TvGenre
import com.alexstibbons.showcase.mapToListOf
import com.alexstibbons.showcase.movieApi.model.Genre
import com.alexstibbons.showcase.movieApi.model.toTvGenreEnum


data class TvListResponse(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<TvShowListItem>)

@Entity
data class TvShowListItem(

    @PrimaryKey
    val id: Int,

    val name: String,

    val overview: String,

    val poster_path: String?,

    val genre_ids: List<Int>

){
    override fun toString(): String = "$name"
    fun toGenreEnum() = genre_ids.mapToListOf { TvGenre.from(it) }
}