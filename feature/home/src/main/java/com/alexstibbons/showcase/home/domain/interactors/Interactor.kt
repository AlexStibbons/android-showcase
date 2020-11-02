package com.alexstibbons.showcase.home.domain.interactors

internal class Interactor (
    val getFilms: GetFilms,
    val getTv: GetTv
) {
    fun clear() {
        getFilms.cancel()
        getTv.cancel()
    }
}