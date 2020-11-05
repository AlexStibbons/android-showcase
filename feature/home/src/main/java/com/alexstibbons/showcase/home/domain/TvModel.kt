package com.alexstibbons.showcase.home.domain

import com.alexstibbons.showcase.MediaList
import com.alexstibbons.showcase.MediaModel
import com.alexstibbons.showcase.TvGenre
import com.alexstibbons.showcase.mapToListOf
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
    override val tvGenreList: List<TvGenre>?
): MediaModel(id, title, promo, imageUrl, null, tvGenreList) {

    override fun toString(): String = title
}

internal fun TvShowListItem.toTvShowDomain() = TvShowDomain(
    id,
    name,
    overview,
    poster_path ?: "",
    tvGenreList = this.toGenreEnum()
)

internal fun TvListResponse.toTvListDomain() = TvListDomain(
    page,
    results.mapToListOf { it.toTvShowDomain() }
)

