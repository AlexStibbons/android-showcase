package com.alexstibbons.showcase.home.domain

import com.alexstibbons.showcase.home.MediaList
import com.alexstibbons.showcase.home.MediaModel
import com.alexstibbons.showcase.mapToListOf
import com.alexstibbons.showcase.tvApi.model.TvListResponse
import com.alexstibbons.showcase.tvApi.model.TvShow

internal data class TvListDomain(
    override val page: Int,
    override val data: List<TvShowDomain>
): MediaList(page, data)

internal data class TvShowDomain(
    override val id: Int,
    override val title: String,
    override val promo: String,
    override val imageUrl: String
): MediaModel(id, title, promo, imageUrl) {

    override fun toString(): String = title
}

internal fun TvShow.toTvShowDomain() = TvShowDomain(
    id,
    name,
    overview,
    poster_path ?: ""
)

internal fun TvListResponse.toTvListDomain() = TvListDomain(
    page,
    results.mapToListOf { it.toTvShowDomain() }
)

