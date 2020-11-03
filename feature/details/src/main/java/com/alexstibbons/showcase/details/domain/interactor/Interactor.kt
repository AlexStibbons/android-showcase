package com.alexstibbons.showcase.details.domain.interactor

internal data class Interactor(
    val getFilmDetails: GetFilmDetails,
    val getTvDetails: GetTvDetails
) {
    fun clear() {
        getFilmDetails.cancel()
        getTvDetails.cancel()
    }
}