package com.alexstibbons.showcase.home.domain.interactors

import com.alexstibbons.showcase.SearchTermsRepo
import com.alexstibbons.showcase.search.SearchTerms

internal fun SearchTerms.toSearchTermsRepo() = SearchTermsRepo(
    mediaType,
    title,
    genreList
)
