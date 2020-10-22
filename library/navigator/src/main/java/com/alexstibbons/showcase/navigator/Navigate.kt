package com.alexstibbons.showcase.navigator

import android.content.Context
import android.content.Intent

object Navigate {

    fun movieList(
        context: Context
    ) = internalIntent(context, "")

    private fun internalIntent(context: Context, action: String): Intent =
        Intent(action).setPackage(context.packageName)
}