package com.alexstibbons.showcase.tvApi.model

import androidx.room.Entity
import androidx.room.PrimaryKey


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

    val poster_path: String?

){
    override fun toString(): String = "$name"
}