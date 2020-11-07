package com.alexstibbons.showcase.database

import androidx.room.Entity
import androidx.room.PrimaryKey

typealias MediaType = Int

@Entity
data class FaveEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val overview: String,
    val genreString: String,
    val imageUrl: String,
    val mediaType: MediaType
)


