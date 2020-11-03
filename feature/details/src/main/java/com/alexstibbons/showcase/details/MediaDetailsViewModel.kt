package com.alexstibbons.showcase.details

import android.util.Log
import androidx.lifecycle.ViewModel
import com.alexstibbons.showcase.MediaType

internal class MediaDetailsViewModel(
    private val mediaType: MediaType,
    private val mediaId: Int
) : ViewModel() {

    init {
        Log.e("vals", "$mediaType AND $mediaId")
    }
}