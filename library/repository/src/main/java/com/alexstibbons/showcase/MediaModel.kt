package com.alexstibbons.showcase

import com.alexstibbons.showcase.database.FaveEntity

abstract class MediaModel(
    open val id: Int,
    open val title: String,
    open val promo: String,
    open val imageUrl: String,
    open val genreList: List<Genre>?,
    open val type: MediaType
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


fun MediaModel.toFaveEntity() = FaveEntity(
    id,
    title,
    promo,
    genreList?.joinToString { it.title } ?: "",
    imageUrl,
    type.id
)

data class SearchTermsRepo(
    val mediaType: MediaType,
    val title: String = "",
    val genreList: List<Genre> = emptyList()
)