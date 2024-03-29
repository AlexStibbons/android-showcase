package com.alexstibbons.showcase.navigator

import android.content.Context
import android.content.Intent
import com.alexstibbons.showcase.navigator.NavigateTo.BundleKeys.FAVE_IDS_ARRAY
import com.alexstibbons.showcase.navigator.NavigateTo.BundleKeys.MEDIA_ID
import com.alexstibbons.showcase.navigator.NavigateTo.BundleKeys.MEDIA_TYPE_ID

object NavigateTo {

    fun movieList(
        context: Context,
        faves: ArrayList<Int>
    ) = internalIntent(context, "com.alexstibbons.showcase.home.open")
        .putExtra(FAVE_IDS_ARRAY, faves)

    fun mediaDetails(
        context: Context,
        typeId: Int,
        mediaId: Int
    ) = internalIntent(context, "com.alexstibbons.showcase.details.open")
        .putExtra(MEDIA_TYPE_ID, typeId)
        .putExtra(MEDIA_ID, mediaId)

    fun about(
        context: Context
    ) = internalIntent(context, "com.alexstibbons.showcase.about.open")

    fun login(
        context: Context
    ) = internalIntent(context, "com.alexstibbons.showcase.login.open")

    fun signin(
        context: Context
    ) = internalIntent(context, "com.alexstibbons.showcase.signin.open")

    fun profile(
        context: Context
    ) = internalIntent(context, "com.alexstibbons.showcase.profile.open")

    private fun internalIntent(context: Context, action: String): Intent =
        Intent(action).setPackage(context.packageName)

    object BundleKeys {
        const val MEDIA_TYPE_ID = "media_type_id"
        const val MEDIA_ID = "media_id"
        const val FAVE_IDS_ARRAY = "fave_ids_array"
    }
}