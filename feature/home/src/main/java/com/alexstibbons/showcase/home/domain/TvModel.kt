package com.alexstibbons.showcase.home.domain

import com.alexstibbons.showcase.*
import com.alexstibbons.showcase.tvApi.model.TvListResponse
import com.alexstibbons.showcase.tvApi.model.TvShowListItem

internal data class TvListDomain(
    override val page: Int,
    override val data: List<TvShowDomain>
): MediaList(page, data)

internal data class TvShowDomain(
    override val id: Int,
    override val title: String,
    override val promo: String,
    override val imageUrl: String,
    override val genreList: List<Genre>?
): MediaModel(id, title, promo, imageUrl, genreList ) {

    override fun toString(): String = title
}

internal fun TvShowListItem.toTvShowDomain() = TvShowDomain(
    id,
    name,
    overview,
    poster_path ?: "",
    genreList = this.toGenresEnum()
)

internal fun TvListResponse.toTvListDomain() = TvListDomain(
    page,
    results.mapToListOf { it.toTvShowDomain() }
)

