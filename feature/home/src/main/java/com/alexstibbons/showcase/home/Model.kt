package com.alexstibbons.showcase.home

internal abstract class MediaModel(
    open val id: Int,
    open val title: String,
    open val promo: String,
    open val imageUrl: String
)

internal abstract class MediaList(
    open val page: Int,
    open val data: List<MediaModel>
)

internal enum class MediaType {
    FILM, TV, FAVE
}