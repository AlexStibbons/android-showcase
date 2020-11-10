package com.alexstibbons.showcase.search

import com.alexstibbons.showcase.Genre
import com.alexstibbons.showcase.MediaType

internal interface OnSearchTermsSelectedCallback {
    fun onSearchDone(data: SearchTerms)
}

interface NotifySearchSelected {
    fun onSearchTermsFilled(data: SearchTerms)
}

data class SearchTerms(
    val mediaType: MediaType,
    val title: String = "",
    val genreList: List<Genre> = emptyList()
)