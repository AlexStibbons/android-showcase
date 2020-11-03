package com.alexstibbons.showcase.details

import android.util.Log
import androidx.lifecycle.ViewModel
import com.alexstibbons.showcase.MediaType
import com.alexstibbons.showcase.details.domain.interactor.Interactor

internal class MediaDetailsViewModel(
    private val mediaType: MediaType,
    private val mediaId: Int,
    private val interactor: Interactor
) : ViewModel() {

    init {
        Log.e("vals", "$mediaType AND $mediaId")
    }
}