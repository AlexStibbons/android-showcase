package com.alexstibbons.showcase

abstract class MediaModel(
    open val id: Int,
    open val title: String,
    open val promo: String,
    open val imageUrl: String
)

abstract class MediaList(
    open val page: Int,
    open val data: List<MediaModel>
)

enum class MediaType(val id: Int) {
    FILM(0), TV(1), FAVE(2);

    companion object {
        fun from(id: Int) = values().find { it.id == id } ?: throw error("No such media type")
    }
}