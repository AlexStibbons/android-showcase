package com.alexstibbons.showcase.navigator

import android.content.Context
import android.content.Intent
import com.alexstibbons.showcase.navigator.NavigateTo.BundleKeys.MEDIA_ID
import com.alexstibbons.showcase.navigator.NavigateTo.BundleKeys.MEDIA_TYPE_ID

object NavigateTo {

    fun movieList(
        context: Context
    ) = internalIntent(context, "com.alexstibbons.showcase.home.open")

    fun mediaDetails(
        context: Context,
        typeId: Int,
        mediaId: Int
    ) = internalIntent(context, "com.alexstibbons.showcase.details.open")
        .putExtra(MEDIA_TYPE_ID, typeId)
        .putExtra(MEDIA_ID, mediaId)

    private fun internalIntent(context: Context, action: String): Intent =
        Intent(action).setPackage(context.packageName)

    object BundleKeys {
        const val MEDIA_TYPE_ID = "media_type_id"
        const val MEDIA_ID = "media_id"
    }
}