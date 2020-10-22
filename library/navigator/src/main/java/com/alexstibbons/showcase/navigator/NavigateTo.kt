package com.alexstibbons.showcase.navigator

import android.content.Context
import android.content.Intent

object NavigateTo {

    fun movieList(
        context: Context
    ) = internalIntent(context, "com.alexstibbons.showcase.home.open")

    private fun internalIntent(context: Context, action: String): Intent =
        Intent(action).setPackage(context.packageName)
}