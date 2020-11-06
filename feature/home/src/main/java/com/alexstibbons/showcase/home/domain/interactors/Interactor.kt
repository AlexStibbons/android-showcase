package com.alexstibbons.showcase.home.domain.interactors

import com.alexstibbons.showcase.database.domain.GetFaves
import com.alexstibbons.showcase.database.domain.RemoveFave
import com.alexstibbons.showcase.database.domain.SaveFave

internal class Interactor (
    val getFilms: GetFilms,
    val getTv: GetTv,
    val saveFave: SaveFave,
    val removeFave: RemoveFave,
    val getFaves: GetFaves
) {
    fun clear() {
        getFaves.cancel()
        removeFave.cancel()
        saveFave.cancel()
        getFilms.cancel()
        getTv.cancel()
    }
}