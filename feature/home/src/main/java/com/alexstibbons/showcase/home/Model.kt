package com.alexstibbons.showcase.home

import com.alexstibbons.showcase.movieApi.model.Movie

internal data class MediaModel(
    val id: Int,
    val title: String,
    val promo: String,
    val imageUrl: String
)

internal enum class MediaType {
    FILM, TV, FAVE
}

internal fun Movie.toMediaModel(): MediaModel = MediaModel(
    this.id,
    this.title,
    this.overview,
    this.poster_path
)

internal fun List<Movie>.toMediaModelList(): List<MediaModel> {
    val list = mutableListOf<MediaModel>()
    this.forEach { list.add(it.toMediaModel()) }
    return list.toList()
}