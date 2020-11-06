package com.alexstibbons.showcase.home.domain.interactors

import com.alexstibbons.showcase.database.domain.SaveFave

internal class Interactor (
    val getFilms: GetFilms,
    val getTv: GetTv,
    val saveFave: SaveFave
) {
    fun clear() {
        saveFave.cancel()
        getFilms.cancel()
        getTv.cancel()
    }
}