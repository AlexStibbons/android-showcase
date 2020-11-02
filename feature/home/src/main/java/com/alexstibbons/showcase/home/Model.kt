package com.alexstibbons.showcase.home

internal abstract class MediaModel(
    open val id: Int,
    open val title: String,
    open val promo: String,
    open val imageUrl: String
)

internal enum class MediaType {
    FILM, TV, FAVE
}