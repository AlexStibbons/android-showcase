package com.alexstibbons.showcase.details.domain.interactor

import com.alexstibbons.showcase.database.domain.GetFaveIds
import com.alexstibbons.showcase.database.domain.RemoveFave
import com.alexstibbons.showcase.database.domain.SaveFave

internal data class Interactor(
    val getFilmDetails: GetFilmDetails,
    val getTvDetails: GetTvDetails,
    val saveFave: SaveFave,
    val removeFave: RemoveFave,
    val getFaves: GetFaveIds
) {
    fun clear() {
        saveFave.cancel()
        removeFave.cancel()
        getFaves.cancel()
        getFilmDetails.cancel()
        getTvDetails.cancel()
    }
}