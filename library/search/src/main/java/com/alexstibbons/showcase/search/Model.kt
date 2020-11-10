package com.alexstibbons.showcase.search

import com.alexstibbons.showcase.Genre

internal interface OnSearchTermsSelectedCallback

interface NotifySearchSelected

data class SearchTerms(
    val title: String = "",
    val genreList: List<Genre> = emptyList()
)