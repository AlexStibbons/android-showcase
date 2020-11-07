package com.alexstibbons.showcase.home.domain

import com.alexstibbons.showcase.*
import com.alexstibbons.showcase.database.FaveEntity

internal class FaveList(
    override val page: Int,
    override val data: List<MediaModel>
): MediaList(page, data)

internal fun FaveEntity.toMediaModel(): MediaModel {
    val mediaType = MediaType.from(this.mediaType)
    return when (mediaType) {
        MediaType.FILM -> MovieDomain(id, title, overview, imageUrl, genreString.toGenreEnum())
        MediaType.TV -> TvShowDomain(id, title, overview, imageUrl, genreString.toGenreEnum())
        else -> error("Error when mapping fave to media model")
    }.exhaustive
}

internal fun String.toGenreEnum(): List<Genre> {
    val stringList: List<String> = this.split(", ")
    return stringList.mapToListOf { Genre.from(it) }
}