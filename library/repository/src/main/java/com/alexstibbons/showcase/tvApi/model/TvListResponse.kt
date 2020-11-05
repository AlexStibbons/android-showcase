package com.alexstibbons.showcase.tvApi.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexstibbons.showcase.Genre
import com.alexstibbons.showcase.mapToListOf
import com.alexstibbons.showcase.movieApi.model.GenreEntity
import com.alexstibbons.showcase.movieApi.model.VideoWrapper


data class TvListResponse(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<TvShowListItemEntity>)

@Entity
data class TvShowListItemEntity(

    @PrimaryKey
    val id: Int,

    val name: String,

    val overview: String,

    val poster_path: String?,

    val genre_ids: List<Int>

){
    override fun toString(): String = "$name"
    fun toGenresEnum() = genre_ids.mapToListOf { Genre.from(it) }
}

data class TvShowDetailsEntity(
    val id: Int,
    val name: String,
    val overview: String,
    val genres: List<GenreEntity>,
    val poster_path: String?,
    val videos: VideoWrapper
) {
    fun toGenresEnum() = genres.mapToListOf { Genre.from(it.id) }
}