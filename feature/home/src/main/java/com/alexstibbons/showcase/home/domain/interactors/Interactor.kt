package com.alexstibbons.showcase.home.domain.interactors

import com.alexstibbons.library.common_domain.GetUserId
import com.alexstibbons.showcase.database.domain.GetFaveIds
import com.alexstibbons.showcase.database.domain.GetFaves
import com.alexstibbons.showcase.database.domain.RemoveFave
import com.alexstibbons.showcase.database.domain.SaveFave

internal class Interactor (
    val getFilms: GetFilms,
    val getTv: GetTv,
    val saveFave: SaveFave,
    val removeFave: RemoveFave,
    val getFaves: GetFaves,
    val getFaveIds: GetFaveIds,
    val getUserId: GetUserId
) {
    fun clear() {
        getFaveIds.cancel()
        getFaves.cancel()
        removeFave.cancel()
        saveFave.cancel()
        getFilms.cancel()
        getTv.cancel()
        getUserId.cancel()
    }
}