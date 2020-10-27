package com.alexstibbons.showcase.home.domain.interactors

internal class Interactor (
    val getFilms: GetFilms
) {
    fun clear() {
        getFilms.cancel()
    }
}